# Tapestry REST support example project

This is a project to exemplify to you can use the REST support introduced in
Tapestry 5.8.0. The classes you should take a look at are UserRest (the implementation of a few REST endpoints), AppModule (specially the coercion and component event result processor contribution) and UserComponentEventResultProcessor (which allows event handler methods to return an User instance and have it automatically mapped to JSON and returned). Don't forget to take a look at app.properties as well, since it's heavily used by the OpenAPI generator.

You can easily run the project by running the RunWithJetty class under src/test/resources. It'll run the webapp under http://localhost:8080. The generated OpenAPI JSON description file is available at http://localhost:8080/openapi.json. The project includes tapestry-openapi-viewer, which embeds Swagger UI (https://swagger.io/tools/swagger-ui/) and is available at http://localhost:8080/opeanapiviewer.
