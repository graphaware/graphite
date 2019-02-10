package com.graphaware.graphite.templates.domain;

import java.util.List;

/**
 * EntityBuilder
 */
public class NodeEntityBuilder {

    public String build(String app, String domainClass, List<RelationshipBuilder> relationshipBuilders) {

        StringBuilder relationships = new StringBuilder();

        relationshipBuilders.forEach(relationshipBuilder -> {
            relationships.append(relationshipBuilder.build());
        });

        return TEMPLATE
                .replace("{app}", app)
                .replace("{domain_class}", domainClass)
                .replace("{relationships}", relationships);

    }

    private static final String TEMPLATE = "package {app}.domain;\n" +
            "\n" +
            "import java.util.Set;\n" +
            "import java.util.List;\n" +
            "\n" +
            "import com.fasterxml.jackson.annotation.JsonIdentityInfo;\n" +
            "import com.voodoodyne.jackson.jsog.JSOGGenerator;\n" +
            "import org.neo4j.ogm.annotation.NodeEntity;\n" +
            "import org.neo4j.ogm.annotation.Relationship;\n" +
            "\n" +
            "@JsonIdentityInfo(generator = JSOGGenerator.class)\n" +
            "@NodeEntity(label = \"{domain_class}\")\n" +
            "public class {domain_class} {\n" +
            "\n" +
            "\tprivate Long id;\n" +
            "\tprivate String name;\n" +
            "\n" +
            "    public Long getId() {\n" +
            "        return this.id;\n" +
            "    }\n" +
            "\n" +
            "    public String getName() {\n" +
            "        return this.name;\n" +
            "    }\n" +
            "\n" +
            "{relationships}\n" +
            "\n" +
            "\tpublic void updateFrom({domain_class} update) {\n" +
            "\t\t// your code goes here\n" +
            "\t}\n" +
            "}";

}
