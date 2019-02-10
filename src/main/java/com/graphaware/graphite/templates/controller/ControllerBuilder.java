package com.graphaware.graphite.templates.controller;

/**
 * ControllerBuilder
 */
public class ControllerBuilder {

    public String build(String app, String domainClass) {

        return TEMPLATE
            .replace("{app}", app)
            .replace("{domain_class}", domainClass)
            .replace("{domain_class_url}", "/api/" + domainClass.toLowerCase());

    }

//    public static void main(String... argv) {
//
//        ControllerBuilder controllerBuilder = new ControllerBuilder();
//        String s = controllerBuilder.build("app", "Student");
//        System.out.println(s);
//
//    }
//
    private static final String TEMPLATE = "package {app}.controller;\n" +
            "\n" +
            "import org.neo4j.ogm.exception.core.NotFoundException;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "import org.springframework.web.bind.annotation.PathVariable;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestMethod;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "import {app}.domain.{domain_class};\n" +
            "import {app}.repository.{domain_class}Repository;\n" +
            "\n" +
            "@RestController\n" +
            "@RequestMapping(value = \"{domain_class_url}\")\n" +
            "public class {domain_class}Controller {\n" +
            "\n" +
            "\tprivate {domain_class}Repository repository;\n" +
            "\n" +
            "\t@Autowired\n" +
            "\tpublic {domain_class}Controller({domain_class}Repository repository) {\n" +
            "\t\tthis.repository = repository;\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(method = RequestMethod.GET)\n" +
            "\tpublic Iterable<{domain_class}> readAll() {\n" +
            "\t\treturn repository.findAll();\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(method = RequestMethod.POST)\n" +
            "\tpublic {domain_class} create(@RequestBody {domain_class} entity) {\n" +
            "\t\treturn repository.save(entity);\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/{id}\", method = RequestMethod.GET)\n" +
            "\tpublic {domain_class} read(@PathVariable Long id) {\n" +
            "\t\treturn repository.findById(id).orElseThrow(NotFoundException::new);\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/{id}\", method = RequestMethod.DELETE)\n" +
            "\tpublic void delete(@PathVariable Long id) {\n" +
            "\t\trepository.deleteById(id);\n" +
            "\t}\n" +
            "\n" +
            "\t@Transactional\n" +
            "\t@RequestMapping(value = \"/{id}\", method = RequestMethod.PUT)\n" +
            "\tpublic {domain_class} update(@PathVariable Long id, @RequestBody {domain_class} update) {\n" +
            "\t\tfinal {domain_class} existing = repository.findById(id).orElseThrow(NotFoundException::new);\n" +
            "\t\texisting.updateFrom(update);\n" +
            "\t\treturn repository.save(existing);\n" +
            "\t}\n" +
            "}";
}
