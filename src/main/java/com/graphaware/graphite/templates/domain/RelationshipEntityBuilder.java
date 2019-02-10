package java.com.graphaware.graphite.templates.domain;

/**
 * RelationshipTemplate
 */
public class RelationshipEntityBuilder {
    
    public String build(String app, String domainClass, String sourceClass, String targetClass, String type) {

        return TEMPLATE
                .replace("{app}", app)
                .replace("{domain_class}", domainClass)
                .replace("{relationship_type}", type)
                .replace("{start_node_class}", sourceClass)
                .replace("{start_node_property}", propertyName(sourceClass))
                .replace("{end_node_class}", targetClass)
                .replace("{end_node_property}", propertyName(targetClass))
                .replace("{get_start_node}", propertyNameGetter(sourceClass))
                .replace("{get_end_node}", propertyNameGetter(targetClass));
    }

    private String propertyNameGetter(String targetClass) {
        return propertyName("get" + targetClass);
    }

    private String propertyName(String targetClass) {

        char[] chArr = targetClass.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        return new String(chArr);
    }

//    private String getTemplate(Path path) {
//        try {
//            return new String(Files.readAllBytes(Paths.get(path.toString())));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void main(String... argv) {

//        Path source = Paths.get("src/main/java/templates/domain/relationship_entity.tpl");

        RelationshipEntityBuilder builder = new RelationshipEntityBuilder();

        String s = builder.build("app", "Register", "Student", "Course", "REGISTERED");

        System.out.println(s);

    }

    private static final String TEMPLATE = "package {app}.domain;\n" +
            "\n" +
            "import com.fasterxml.jackson.annotation.JsonIdentityInfo;\n" +
            "import com.voodoodyne.jackson.jsog.JSOGGenerator;\n" +
            "import org.neo4j.ogm.annotation.RelationshipEntity;\n" +
            "import org.neo4j.ogm.annotation.StartNode;\n" +
            "import org.neo4j.ogm.annotation.EndNode;\n" +
            "\n" +
            "@JsonIdentityInfo(generator = JSOGGenerator.class)\n" +
            "@RelationshipEntity(type = \"{relationship_type}\")\n" +
            "public class {domain_class} {\n" +
            "\n" +
            "\tprivate Long id;\n" +
            "\n" +
            "    @StartNode private {start_node_class} {start_node_property}\n" +
            "    @EndNode   private {end_node_class} {end_node_property}\n" +
            "\n" +
            "    public Long getId() {\n" +
            "        return this.id;\n" +
            "    }\n" +
            "\n" +
            "    public {start_node_class} {get_start_node}() {\n" +
            "        return this.{start_node_property};\n" +
            "    }\n" +
            "\n" +
            "    public {end_node_class} {get_end_node}() {\n" +
            "        return this.{end_node_property};\n" +
            "    }\n" +
            "\n" +
            "\tpublic void updateFrom({domain_class} update) {\n" +
            "        // your code goes here\n" +
            "\t}\n" +
            "}\n";
}
