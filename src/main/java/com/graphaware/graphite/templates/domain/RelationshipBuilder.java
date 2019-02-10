package com.graphaware.graphite.templates.domain;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * RelationshipTemplate
 */
public class RelationshipBuilder {

    private final Path templatePath = Paths.get("src/main/java/templates/domain/relationship.tpl");

    private final String targetClass;
    private final String type;
    private final String direction;
    private final String collectionType;

    public RelationshipBuilder(String targetClass, String type, String direction, String collectionType) {
        this.targetClass = targetClass;
        this.type = type;
        this.direction = direction;
        this.collectionType = collectionType;
    }
    public String build() {

        return TEMPLATE
                .replace("{target_class}", targetClass)
                .replace("{type}", type)
                .replace("{direction}", direction)
                .replace("{start_collection_type}", startCollectionType(collectionType))
                .replace("{end_collection_type}", endCollectionType(collectionType))
                .replace("{property_name}", propertyName(targetClass, collectionType))
                .replace("{set_property_name}", propertyNameSetter(targetClass, collectionType))
                .replace("{get_property_name}", propertyNameGetter(targetClass, collectionType));

    }

    private String propertyNameSetter(String targetClass, String type) {
        return propertyName("set" + targetClass, type);
    }

    private String propertyNameGetter(String targetClass, String type) {
        return propertyName("get" + targetClass, type);
    }

    private String propertyName(String targetClass, String type) {

        char[] chArr = targetClass.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        if (type != null && type.length() > 0) {
            return new String(chArr).concat("s");
        }
        return new String(chArr);
    }

    private String startCollectionType(String collectionType) {
        switch (collectionType.toLowerCase()) {
            case "set":
                return "Set<";
            case "list":
                return "List<";
            default:
                return "";
        }
    }

    private String endCollectionType(String collectionType) {
        switch (collectionType.toLowerCase()) {
            case "set":
            case "list":
                return ">";
            default:
                return "";
        }
    }

//    private String getTemplate(Path path) {
//        try {
//            return new String(Files.readAllBytes(Paths.get(path.toString())));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void main(String... argv) {

        RelationshipBuilder builder =
                new RelationshipBuilder("Course", "ENROLLED_IN", "INCOMING", "List");


        String s = builder.build();

        System.out.println(s);
    }

    private static final String TEMPLATE =
            "    @Relationship(type = \"{type}\", direction=Relationship.{direction})\n" +
            "    private {start_collection_type}{target_class}{end_collection_type} {property_name};\n" +
            "\n" +
            "    public {start_collection_type}{target_class}{end_collection_type} {get_property_name}() {\n" +
            "        return {property_name};\n" +
            "    }\n" +
            "\n" +
            "    public void {set_property_name}({start_collection_type}{target_class}{end_collection_type} value) {\n" +
            "        this.{property_name} = value;\n" +
            "    }\n\n";
}
