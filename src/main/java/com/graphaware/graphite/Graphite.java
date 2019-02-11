package com.graphaware.graphite;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.graphaware.graphite.templates.SpringBootNeo4jApplicationBuilder;
import com.graphaware.graphite.templates.bootstrap.BootstrapControllerBuilder;
import com.graphaware.graphite.templates.bootstrap.BootstrapServiceBuilder;
import com.graphaware.graphite.templates.controller.ControllerBuilder;
import com.graphaware.graphite.templates.domain.NodeEntityBuilder;
import com.graphaware.graphite.templates.domain.RelationshipBuilder;
import com.graphaware.graphite.templates.maven.PomBuilder;
import com.graphaware.graphite.templates.repository.RepositoryBuilder;
import org.yaml.snakeyaml.Yaml;

/**
 * Graphite
 */
public class Graphite {

    private Map<String, Object> config;

    public void load() throws IOException {
        System.out.println("Loading configuration from 'config/graphite.yml'");
        Path path = Paths.get("config/graphite.yml");
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(path.toString());
        this.config = yaml.load(inputStream);
    }

    public String groupId() {
        return (String) config.get("groupId");
    }

    public String artifactId() {
        return (String) config.get("artifactId");
    }

    public String version() {
        return (String) config.get("version");
    }

    public String name() {
        return (String) config.get("name");
    }

    public String description() {
        return (String) config.get("description");
    }

    public List<Map<String, String>> schema() {
        return (List<Map<String, String>>) config.get("schema");
    }

    private Path buildPath() {
        return Paths.get(".", name());
    }

    private String rootPackagePath() {
        return buildPath().toString() + "/src/main/java/" + this.groupId().concat(File.separator).concat(this.artifactId()).replace(".", File.separator).replace("-", File.separator);
    }

    private String rootResourcePath() {
        return buildPath().toString() + "/src/main/resources";
    }

    private String rootPackageName() {
        return this.groupId().concat(".").concat(this.artifactId()).replace("-", ".");
    }

    private Set<String> domainClassNames() {
        Set<String> domainClassNames = new HashSet<>();

        domainClassNames.addAll(schema().stream().map(e -> e.get("source").replace("+", "")).collect(Collectors.toList()));
        domainClassNames.addAll(schema().stream().map(e -> e.get("target").replace("+", "")).collect(Collectors.toList()));

        return domainClassNames;
    }

    private List<RelationshipBuilder> relationshipBuilders(String domainClass) {
        List<RelationshipBuilder> relationshipBuilders = new ArrayList<>();
        Set<String> keys = new HashSet<>();

        schema().forEach(e -> {
            String source = e.get("source");
            String target = e.get("target");
            String type = e.get("type");
            String collectionType = "";

            String key = domainClass + ":" + type + target.replace("+", "");

            if (source.replace("+", "").equals(domainClass)) {
                if (target.contains("+")) {
                    collectionType = "Set";
                }
                if (!keys.contains(key)) {
                    keys.add(key);
                    relationshipBuilders.add(new RelationshipBuilder(target.replace("+", ""), type, "OUTGOING", collectionType));
                }
            }

            if (target.replace("+", "").equals(domainClass)) {
                if (source.contains("+")) {
                    collectionType = "Set";
                }
                if (!keys.contains(key)) {
                    keys.add(key);
                    relationshipBuilders.add(new RelationshipBuilder(source.replace("+", ""), type, "INCOMING", collectionType));
                }
            }
        });

        return relationshipBuilders;
    }

    private void createFolders() throws Exception {

        System.out.println("Creating source folders");

        String rootPackage = rootPackagePath();

        Files.createDirectories(Paths.get(rootPackage));
        Files.createDirectories(Paths.get(rootPackage, "controller"));
        Files.createDirectories(Paths.get(rootPackage, "service"));
        Files.createDirectories(Paths.get(rootPackage, "domain"));
        Files.createDirectories(Paths.get(rootPackage, "repository"));
        Files.createDirectories(Paths.get(rootPackage, "events"));

        Files.createDirectories(Paths.get(rootResourcePath()));
    }
    
    private void writePom() {
        System.out.println("Creating Maven project " + this.name());
        String pom = new PomBuilder().build(this);
        write(Paths.get(buildPath().toString(), "pom.xml"), pom);
    }

    private void writeBootstrapper() {
        System.out.println("Creating Bootstrap Service");
        String service = new BootstrapServiceBuilder().build(rootPackageName());
        write(Paths.get(rootPackagePath(), "service", "BootstrapService.java"), service);
        String controller = new BootstrapControllerBuilder().build(rootPackageName());
        write(Paths.get(rootPackagePath(), "controller", "BootstrapController.java"), controller);
    }


    private void writeRepositoryClasses() {

        System.out.println("Creating Spring Data Neo4j repositories");

        String rootPackageName = rootPackageName();
        String rootPackagePath = rootPackagePath();

        domainClassNames().forEach(domainClass -> {
            String repository = new RepositoryBuilder().build(rootPackageName, domainClass);
            write(Paths.get(rootPackagePath, "repository", domainClass + "Repository.java"), repository);
        });
    }

    private void writeControllerClasses() {

        System.out.println("Creating controllers");

        String rootPackageName = rootPackageName();
        String rootPackagePath = rootPackagePath();

        domainClassNames().forEach(domainClass -> {
            String controller = new ControllerBuilder().build(rootPackageName, domainClass);
            write(Paths.get(rootPackagePath, "controller", domainClass + "Controller.java"), controller);
        });
    }

    private void writeDomainClasses() {

        System.out.println("Creating domain entity classes");

        String rootPackageName = rootPackageName();
        String rootPackagePath = rootPackagePath();

        domainClassNames().forEach(domainClass -> {
            String entity = new NodeEntityBuilder().build(rootPackageName, domainClass, relationshipBuilders(domainClass));
            write(Paths.get(rootPackagePath, "domain", domainClass + ".java"), entity);
        });
    }

    private void writeApplicationClass() {
        System.out.println("Creating Spring Boot Application");
        String app = new SpringBootNeo4jApplicationBuilder().build(rootPackageName());
        write(Paths.get(rootPackagePath(), "Application.java"), app);
    }

    private void copyBootstrapFile() {

        System.out.println("Copying bootstrap files");

        Path source = Paths.get("config/bootstrap.cql");
        Path target = Paths.get(rootResourcePath(), "bootstrap.cql");

        if (Files.exists(source)) {
            try {
                Files.copy(source, target, REPLACE_EXISTING);
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    private void write(Path target, String contents) {
        try {
            System.out.println("Writing " + target);
            Files.write(target, contents.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... argv) throws Exception {

        Graphite graphite = new Graphite();

        graphite.load();
        graphite.createFolders();
        graphite.writePom();
        graphite.writeApplicationClass();
        graphite.writeBootstrapper();
        graphite.writeRepositoryClasses();
        graphite.writeControllerClasses();
        graphite.writeDomainClasses();
        graphite.copyBootstrapFile();

        System.out.println("All done");

    }

}
