<h1>Template service</h1>

Template service has the required basic dependencies to implement your microservice. And also for your reference sample code is written. So that you can follow the same. When you write the service. 

<h2>How to write the new service using Template Service</h2>

1. Download the Zip file of the repo and extract.
2. Open it using your favorite IDE.
3. Change the artifactId, name and description of the service in the pom file.
4. Refactor the project structure 
5. Initially the main package name is com.qbitum.template. Change it to com.qbitum. < your service name>
6. Change the main method class name to your service name (TemplateserviceApplication ->  <your service name>Application).
7. Change the main test method class name to your service main test class name. ( TemplateserviceApplicationTests -> <your service name>ApplicationTests)
8. Now create a new git repository for your service.
9. Add your new service to the git repository.
      - git init
      - git remote add origin `<git repo URL for your repository>`
      - git add –all
      - git commit -m ‘initial commit’ 
      - git push origin main.
10. After successfully push your service to git then run maven clean install to install dependencies.
11. Initially registering to spring cloud discovery is disabled so when you are going to build service in jenkins pipeline, enable service to connect with spring cloud discovery. ( we are working on improving profile based config server enabling and disabling, will give it as soon as possible).

<h2>OpenApi Guidelines</h2>

- Use OpenApi generator to generate APIs and DTOs
- Create an API specification yaml file according to [Swagger OpenApi Specification](https://swagger.io/docs/specification)
- One API spec for one <b>entire Project</b>
- Add the path of the api spec yaml to the openApi generator plugin(in the pom.xml file)

      <plugin>
          <groupId>org.openapitools</groupId>
          <artifactId>openapi-generator-maven-plugin</artifactId>
          <version>7.2.0</version>
          <executions>
            <execution>
            <configuration>
                <inputSpec>
                   ${project.basedir}/src/main/resources/openapi/api.yml
                </inputSpec>
                  ......
            </configuration>
            </execution>
          </executions>
      </plugin>
- Run `mvn clean install` or `mvn clean compile` to generate OpenApi code 
- It will be generated inside `target/generated-sources/openapi` <b>(if there is an issue try reloading maven)</b>
- Implement the generated delegate interface(examples are provided) and start coding. 
- The defined APIs can be viewed on swagger at `http://{host}:{port}/swagger-ui-custom.html`
- By default, all defined apis on the spec will be shown in the swagger UI. This can be edited in the `src/main/java/com.qbitum.{servicename}/configuration/SwaggerConfig.java`
- It is advised to use a common word in the api's url specific to the service it belongs to.
- <b>Again, if there is an issue after compiling try reloading maven : \ </b>

<h2>Logging Guidelines</h2>

The structure of logs for all services should be consistent, therefore use Slf4j logging. Annotate the relevant class wih @Slf4j. A logging pattern is defined the application properties.

      logging:
            pattern:
                  console: "%d{yyyy-MM-dd HH:mm:ss} [%X{traceId:-},%X{spanId:-}] ${LOG_LEVEL_PATTERN:-%5p} %m%n"
Example:</br>

      2024-04-03 15:25:53 [xxxxxxxxxxxxxxxx,xxxxxxxxxxxxxxxx] - INFO Netty started on port 9095
Example usage:

      Object obj1;
      Object obj2;
      log.debug("Concise description for the log : {} , {}", obj1, obj2);
- Logging level can be adjusted from application.yml file
- Log file location and log rotation methods are provided under `logging:` in application.yml
- You can use our custom annotations `@LogMonoExecutionTime`, `@LogFluxExecutionTime` to measure execution time of a method and `@LogMethodParams` measure method request.

<h2>Exception Handling</h2>

We use a custom error handling system in oder to pinpoint where an error originated. And this system should be strictly followed by all developer.
Please note that this process is still in the experimental phase.

- All errors originated withing the service ie; unauthorized api error, to all other errors with Http Status code '500' will be thrown as a Http response entity having a `ServerErrorResponse` object, as its body, and  wrapped with status code 500
- The probable errors are specified in the `ServerError` Enum class<br>.

example :

    500
    Error: Internal Server Error
    
    Response body
    {
    "code": 510,
    "status": "ERROR",
    "message": "This is service forbiden",
    "errorPath": "com.qbitum.template.service.impl.SampleServiceImpl.stringGenerator"
    }
- All errors originating in the service and data layers should be handled and passed as `Mono.error(new ServerErrorException(...))`
- These exceptions should be finally caught and thrown as `throw new DelegateResponseException(error);`<br>

example:</br>
<h4>Passing exception from service layers</h4>
    
    public Mono<String> getString() {
        return serviceString().flatMap(Mono::just)
            .onErrorResume(error -> {
                return Mono.error(error);
            });
    }

    public Mono<String> stringGenerator() {
        return Mono.error(new ServerErrorException(ServerError.ERROR_UNDEFINED,SourcePath.findCurrentPath()));
    }
<h4>Handling the exception at delegate level</h4>

    @Overide
    public Mono<ResponseEntity<String>> getData() {

        return sampleService.getString()
            .flatMap(str -> {
                log.debug("Returning Str : {}",str);
                return Mono.just(ResponseEntity.ok().body(str));
            })
            .doOnSuccess(data -> log.debug("Successfully completed"))
            .doOnError(error -> {
                log.error("Exception from service layer");
                throw new DelegateResponseException(error,.....);
            });
    }
<h4>Handling Exceptions with WebClient Requests. (remember to cast the body to `ServerErrorResponse` object)</h4>

    return webClientBuilder
        .build()
        .get()
        .uri("http://localhost:9541/getString")
        .exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode() == HttpStatusCode.valueOf(200)) {
                return clientResponse.bodyToMono(String.class);
            } else if (clientResponse.statusCode() == HttpStatusCode.valueOf(500)) {
                return clientResponse.bodyToMono(ServerErrorResponse.class)
                    .flatMap(error -> {
                        return Mono.error(new ServerErrorException(error));
                    });
            } else {
                // Make sure to mention the response status code
                String description = "Error recived of Http status : " + clientResponse.statusCode().toString();
                return Mono.error(new ServerErrorException(ServerError.UNDEFINED_WEBCLIENT_ERROR,"http://localhost:9541/getString", description ));
            }
        })
        .doOnError(error -> {
            throw new DelegateResponseException(error);
        });
- Here ServerError is an Enum class and SourcePath is a Util to get the path to the origin current location
- If you come across any issues while following this process or if you have better suggestions for error handling, please contact the Qbitum Backend platform team ; )

<h2>Guidelines when you write and committing code</h2>

1. We are following the reactive programming paradigm for coding. So when you use any extra dependencies please make sure that it supports reactive programming.
2. Committing code using IDE is strictly prohibited. Use the command line to commit the code. 
3. Writing unit tests are a must.


**For any further clarifications please contact the platform core back-end team.**

**Congratulations now you are ready to code in your new service.**<br>
**Don't forget to update this README file : /**
