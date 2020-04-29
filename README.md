# JAVA WEBAPPLICATION BASE
An example setup to kickstart building a java webapplication, 
with a bunch of configuration examples

## EXAMPLES
### Configuration
* How to configure an Embedded Jetty Server with RestEasy and Jackson
to serve static frontend files and also REST endpoints
* How to connect to a H2 in-memory database
* How to serve static content from resources folder for development
* How to configure logging with Logback and Slf4j
### REST API
* How to do basic REST endpoints
* How to process form data in an endpoint
* How to process json data in an endpoint
* How to map exceptions to the desired responses
* How to validate a JSON parsed object during initialization
### Testing
* How to do basic unit testing with Junit5
* How to do basic REST API integration testing with RestAssured
* How to do basic End-to-End testing with Selenium

## DEVELOPMENT OPTIONS
### Frontend
When developing the frontend, it comes in handy if the application can 
use the files directly from their source without installing them to the target folder.

To enable this, run the application with the `static-content-source-folder` 
option and provide the full path to the frontend files location.

For example:
```-Dstatic-content-source-folder="/full/path/to/java-webapplication/src/main/resources/mdhtr/webapplication/public"```
