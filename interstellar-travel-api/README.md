# Interstellar Travel API Server

This project is based off the spring-boot initializer project one can start with (https://start.spring.io/) version 2.2.4.RELEASE

## Development

Use your favourite IDE and let it do the magic. This is a standard maven based project so should work out of the box with most IDEs or using mvn commands - that's your call.

## Important endpoints

Once your server is up and running, your API base should be `http://localhost:8080/api/v1/{api}`
Basic API docs will be avalable [here](http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs&validatorUrl=#/)
The SOAP wsdl will be available [here](http://localhost:8080/ws/routes.wsdl)

## Running end-to-end tests

Depending upon the IDE you use, the tests should fire the moment you build your project. If not, just run the tests manually.
