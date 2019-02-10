package com.graphaware.graphite.templates.repository;

/**
 * RepositoryBuilder
 */
public class RepositoryBuilder {

    public String build(String app, String domainClass) {

        return TEMPLATE
                .replace("{app}", app)
                .replace("{domain_class}", domainClass);

    }

//    public static void main(String... argv) {
//
//        String app = "app";
//        String domainClass = "Test";
//
////        Path source = Paths.get("src/main/java/templates/repository/repository.tpl");
////        Path target = Paths.get("src/main/java/school/repository/" + domainClass + "Repository.java");
//
//        RepositoryBuilder builder = new RepositoryBuilder();
//
//        builder.build(app, domainClass);
//
//    }
//
    private static final String TEMPLATE = "package {app}.repository;\n" +
            "\n" +
            "import org.springframework.data.repository.CrudRepository;\n" +
            "import {app}.domain.{domain_class};\n" +
            "\n" +
            "public interface {domain_class}Repository extends CrudRepository<{domain_class}, Long> {\n" +
            "\n" +
            "}";

}
