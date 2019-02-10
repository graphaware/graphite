package com.graphaware.graphite.templates.maven;

import com.graphaware.graphite.Graphite;

/**
 * PomBuilder
 */
public class PomBuilder {

    public String build(Graphite app) {

        return TEMPLATE
            .replace("{app.group_id}", app.groupId())
            .replace("{app.artifact_id}", app.artifactId())
            .replace("{app.version}", app.version())
            .replace("{app.name}", app.name())
            .replace("{app.description}", app.description());

    }

    private static final String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <modelVersion>4.0.0</modelVersion>\n" +
            "\n" +
            "    <groupId>{app.group_id}</groupId>\n" +
            "    <artifactId>{app.artifact_id}</artifactId>\n" +
            "    <version>{app.version}</version>\n" +
            "    <packaging>jar</packaging>\n" +
            "\n" +
            "    <name>{app.name}</name>\n" +
            "    <description>{app.description}</description>\n" +
            "\n" +
            "    <parent>\n" +
            "        <groupId>org.springframework.boot</groupId>\n" +
            "        <artifactId>spring-boot-starter-parent</artifactId>\n" +
            "        <version>2.0.5.RELEASE</version>\n" +
            "        <relativePath/> <!-- lookup parent from repository -->\n" +
            "    </parent>\n" +
            "\n" +
            "    <properties>\n" +
            "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
            "        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>\n" +
            "    </properties>\n" +
            "\n" +
            "    <dependencies>\n" +
            "\n" +
            "        <dependency>\n" +
            "            <groupId>org.springframework.boot</groupId>\n" +
            "            <artifactId>spring-boot-starter-data-neo4j</artifactId>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <dependency>\n" +
            "            <groupId>org.springframework.boot</groupId>\n" +
            "            <artifactId>spring-boot-starter-web</artifactId>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <dependency>\n" +
            "            <groupId>org.springframework.boot</groupId>\n" +
            "            <artifactId>spring-boot-starter-test</artifactId>\n" +
            "            <scope>test</scope>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <dependency>\n" +
            "            <groupId>com.voodoodyne.jackson.jsog</groupId>\n" +
            "            <artifactId>jackson-jsog</artifactId>\n" +
            "            <version>1.1</version>\n" +
            "            <scope>compile</scope>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <!-- uncomment to use embedded -->\n" +
            "        <dependency>\n" +
            "            <groupId>org.neo4j</groupId>\n" +
            "            <artifactId>neo4j-ogm-embedded-driver</artifactId>\n" +
            "            <version>${neo4j-ogm.version}</version>\n" +
            "            <scope>compile</scope>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <dependency>\n" +
            "            <groupId>org.neo4j</groupId>\n" +
            "            <artifactId>neo4j</artifactId>\n" +
            "            <version>3.1.0</version>\n" +
            "            <scope>runtime</scope>\n" +
            "        </dependency>\n" +
            "\n" +
            "        <!-- uncomment to use http -->\n" +
            "        <!--<dependency>-->\n" +
            "            <!--<groupId>org.neo4j</groupId>-->\n" +
            "            <!--<artifactId>neo4j-ogm-http-driver</artifactId>-->\n" +
            "            <!--<version>${neo4j-ogm.version}</version>-->\n" +
            "        <!--</dependency>-->\n" +
            "\n" +
            "        <!-- uncomment to use bolt -->\n" +
            "        <!--<dependency>-->\n" +
            "        <!--<groupId>org.neo4j</groupId>-->\n" +
            "        <!--<artifactId>neo4j-ogm-bolt-driver</artifactId>-->\n" +
            "        <!--<version>${neo4j-ogm.version}</version>-->\n" +
            "        <!--</dependency>-->\n" +
            "\n" +
            "    </dependencies>\n" +
            "\n" +
            "    <build>\n" +
            "        <plugins>\n" +
            "            <plugin>\n" +
            "                <groupId>org.springframework.boot</groupId>\n" +
            "                <artifactId>spring-boot-maven-plugin</artifactId>\n" +
            "            </plugin>\n" +
            "        </plugins>\n" +
            "    </build>\n" +
            "\n" +
            "</project>";

}
