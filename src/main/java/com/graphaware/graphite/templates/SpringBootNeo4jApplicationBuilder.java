package com.graphaware.graphite.templates;

/**
 * SpringBootNeo4jApplicationBuilder
 */
public class SpringBootNeo4jApplicationBuilder {

    public String build(String app) {
        return TEMPLATE.replace("{app}", app);
    }

    private static final String TEMPLATE = "package {app};\n" +
            "\n" +
            "import org.springframework.boot.SpringApplication;\n" +
            "import org.springframework.boot.autoconfigure.SpringBootApplication;\n" +
            "import org.springframework.boot.autoconfigure.domain.EntityScan;\n" +
            "import org.springframework.context.annotation.Bean;\n" +
            "import org.springframework.context.event.EventListener;\n" +
//            "import {app}.events.EventPublisher;\n" +
//            "import {app}.events.PostSaveEvent;\n" +
//            "import {app}.events.PreDeleteEvent;\n" +
//            "import {app}.events.PreSaveEvent;\n" +
            "\n" +
            "\n" +
            "@SpringBootApplication\n" +
            "@EntityScan(\"{app}.domain\")\n" +
            "public class Application {\n" +
            "\n" +
            "\tpublic static void main(String[] args) {\n" +
            "\t\tSpringApplication.run(Application.class, args);\n" +
            "\t}\n" +
//            "\n" +
//            "\t/**\n" +
//            "\t * Simply defining a Neo4j OGM <code>EventListener</code> will register it\n" +
//            "\t * with the session factory.\n" +
//            "\t */\n" +
//            "\t@Bean\n" +
//            "\tpublic EventPublisher eventPublisher() {\n" +
//            "\t\treturn new EventPublisher();\n" +
//            "\t}\n" +
//            "\n" +
//            "\n" +
//            "\t@EventListener\n" +
//            "\tpublic void onPreSaveEvent(PreSaveEvent event) {\n" +
//            "\t\tObject entity = event.getSource();\n" +
//            "\t\tSystem.out.println(\"Before save of: \" + entity);\n" +
//            "\t}\n" +
//            "\n" +
//            "\t@EventListener\n" +
//            "\tpublic void onPostSaveEvent(PostSaveEvent event) {\n" +
//            "\t\tObject entity = event.getSource();\n" +
//            "\t\tSystem.out.println(\"After save of: \" + entity);\n" +
//            "\t}\n" +
//            "\n" +
//            "\t@EventListener\n" +
//            "\tpublic void onPreDeleteEvent(PreDeleteEvent event) {\n" +
//            "\t\tObject entity = event.getSource();\n" +
//            "\t\tSystem.out.println(\"Before delete of: \" + entity);\n" +
//            "\t}\n" +
            "}";
}
