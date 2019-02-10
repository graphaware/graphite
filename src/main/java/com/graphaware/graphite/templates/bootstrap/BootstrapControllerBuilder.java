package com.graphaware.graphite.templates.bootstrap;

/**
 * BootstrapControllerBuilder
 */
public class BootstrapControllerBuilder {

    public final String build(String app) {
        return TEMPLATE.replace("{app}", app);
    }

    private static final String TEMPLATE = "package {app}.controller;\n" +
            "\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.http.HttpStatus;\n" +
            "import org.springframework.http.ResponseEntity;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "import {app}.service.BootstrapService;\n" +
            "\n" +
            "@RestController\n" +
            "public class BootstrapController {\n" +
            "\n" +
            "\tprivate BootstrapService service;\n" +
            "\n" +
            "\t@Autowired\n" +
            "\tpublic BootstrapController(BootstrapService service) {\n" +
            "\t\tthis.service = service;\n" +
            "\t}\n" +
            "\n" +
            "\t@Transactional\n" +
            "\t@RequestMapping(\"/api/bootstrap\")\n" +
            "\tpublic ResponseEntity bootstrap() {\n" +
            "\n" +
            "\t\tservice.clearDatabase();\n" +
            "\t\tservice.load();\n" +
            "\n" +
            "\t\treturn new ResponseEntity<Void>(HttpStatus.CREATED);\n" +
            "\t}\n" +
            "\n" +
            "}";
}
