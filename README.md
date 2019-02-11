# Graphite

Graphite is a Spring Boot application code generator for Spring Data Neo4j-based applications.

Graphite is driven by a single configuration file, which must be called `graphite.yml` and must be in the directory you invoke the `graphite` command from.  

This file defines the application name, description, maven group and artifact ids, etc, together with a simple graph schema that will be used to build the application. 

Example:

```yaml
groupId: com.graphaware
artifactId: graphite.demo
version: 1.0-SNAPSHOT
name: graphite-demo
description: Demo graphite application
schema: [
  {
    source: School, target: Teacher+, type: SCHOOL_TEACHER
  },
  {
    source: Teacher+, target: Course+, type: TEACHER_COURSE
  },
  {
    source: Course, target: Subject+, type: COURSE_SUBJECT
  },
  {
    source: Student+, target: Register+, type: STUDENT_REGISTER
  },
  {
    source: Register, target: Course, type: REGISTER_COURSE
  }
]
``` 

Each `source` and `target` attribute represents a node label you want to persist, with the `type` attribute being the name of a relationship that you want to exist between them.

The `+` sign appended to the node labels indicates their cardinality constraints. These are used when generating the Spring Data Neo4j classes that will map to the schema.

Using the information in the config file Graphite will generate a ready-to-run maven-based Spring Boot application, using a Neo4j database, along with all the required domain classes and associated controllers and Spring repositories. 

The generated application is created in the directory you run it from (i.e. the directory containing the `graphite.yml` config file), in a folder corresponding to the application name being generated.
Is is pre-configured to use an embedded instance of Neo4j, but this can be changed by editing the pom file if you want to use an existing Neo4j instance.

## Install

```
brew tap graphaware/graphite
brew install graphite
```

Or, download the project source code and build it locally (see "Try the demo app" below)


## Generate
```
mkdir -p myApp/config
```

```
cd myApp
```


Configure the `config/graphite.yml` file as described above then build the application  

`graphite`

## Run
To run the application, `cd` into the newly created application directory and type `mvn spring-boot:run` at the command line.

## Application API
The generated application api endpoints follow canonical REST standards. 

```GET api/student``` will fetch all `Student` entities

```GET api/student/id=``` will fetch a `Student` entity with the specified id.

You can of course add additional endpoints after the application has been generated


## Bootstrapping data

Add a `bootstrap.cql` file containing Cypher statements into the config directory. It will be copied to `src/main/resources` of the generated application. When the app starts import your bootstrap data into the database, either from the browser:

```http://localhost:8080/api/bootstrap``` 

or from a terminal, using `cURL`:

```curl http://localhost:8080/api/bootstrap```

## JSON serialisation
The server publishes responses in JSON format, using the JSOG serialiser. This serialiser automatically handles recursive references between entities, so there is no need to annotate any of the generated domain classes with `@JsonIgnore`. The JSOG serialiser is also available as a Javascript library, and should be used when parsing responses in a web client (see "Web client" below) 


## Web client
A simple CRUD-style web client can also be generated. Only the Angular framework is currently supported. To generate a web client, pass a `client` argument when invoking graphite:

```graphite -client=angular```  

## Try the demo app

Build the project:

```git clone git@github.com:graphaware/graphite.com```

```mvn clean package```

Create the demo spring boot app

```java -jar target/graphite.jar```

Run it

```cd graphite-demo```

```mvn spring-boot:run```


