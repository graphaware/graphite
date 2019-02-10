package com.graphaware.graphite.templates.bootstrap;

/**
 * BootstrapServiceBuilder
 */
public class BootstrapServiceBuilder {

    public final String build(String app) {
        return TEMPLATE.replace("{app}", app);
    }
    
    private static final String TEMPLATE = "package {app}.service;\n" +
            "\n" +
            "import org.neo4j.ogm.session.Session;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "\n" +
            "import java.io.BufferedReader;\n" +
            "import java.io.InputStreamReader;\n" +
            "import java.util.Collections;\n" +
            "\n" +
            "@Service\n" +
            "public class BootstrapService {\n" +
            "\n" +
            "\tprivate Session session;\n" +
            "\n" +
            "\t@Autowired\n" +
            "\tpublic BootstrapService(Session session) {\n" +
            "\t\tthis.session = session;\n" +
            "\t}\n" +
            "\n" +
            "\t@Transactional\n" +
            "\tpublic void clearDatabase() {\n" +
            "\t\tsession.purgeDatabase();\n" +
            "\t}\n" +
            "\n" +
            "\t@Transactional\n" +
            "\tpublic void load() {\n" +
            "\t\tStringBuilder sb = new StringBuilder();\n" +
            "\t\tBufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(\"bootstrap.cql\")));\n" +
            "\t\tString line;\n" +
            "\t\ttry {\n" +
            "\t\t\twhile ((line = reader.readLine()) != null) {\n" +
            "\t\t\t\tsb.append(line);\n" +
            "\t\t\t\tsb.append(\" \");\n" +
            "\t\t\t}\n" +
            "\t\t} catch (Exception e) {\n" +
            "\t\t\tthrow new RuntimeException(e);\n" +
            "\t\t}\n" +
            "\t\tString cqlFile = sb.toString();\n" +
            "\t\tsession.query(cqlFile, Collections.EMPTY_MAP);\n" +
            "\t}\n" +
            "}";
}
