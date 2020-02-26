# Interstellar-travel

## Technology and design decisions

1. Spring Boot used at API end
2. Angular 8 used at Front end
3. Derby DB to store planet data
4. Hibernate used as ORM tool
5. Spring Security used with basic auth for now. Basic Auth added for now with a fixed user credentials. Credentials can be found in application.properties.
6. JUnit used for unit testing at API end.
7. Front end codebase kept separate for maintaining separation of concerns and better management of code.
8. [Nebular](https://akveo.github.io/nebular/ "Nebular") used as a front end theme for UI implementation.
9. CORS enabled at API end in Spring security to facilitate API calling from front end. Allowed domains setup from application.properties.
10. Shortest path algorithm is written as an independent utility which is agnostic of node entity types. It's completely based on Vertex, Edge and Graph POJO classes. This allows reusability of same algorithm to any other entity than Planets in future .
11. An adapter service created which works on converting Planet/Route to Vertex/Edge.
12. Login feature added both at front end and back end. A simple login API returning basic auth token after successful verification.
13. Data setup done using excel upload feature. File uploaded from front end and stored/parsed at backend to seed data into DB.
14. Both REST as well as SOAP API created to get shortest path between planets.

## Assumptions

1. Every new Excel upload will mean a new data seed and any old data will be cleard.
2. Route has a foreign key mapping with Planet for source and destination columns. So, any Route with a Planet which is not there in Planet table will be ignored while data seed from file.
3. DELETE and PUT operations for Planets are not required from front end.
4. Creation of data only happens via the Excel upload.
5. REST and SOAP apis are both accepting Planet names as source and destination parameters instead of Planet nodes. This was done purposely for making it easier for me to visually see the data. If these APIs are consumed by another system, we can easily switch from planet names to planet nodes.

## Future Considerations

1. Better Exception handling
2. Better unit test case coverage
3. Basic Auth should be replaced with OAuth + JWT strategy
4. Improve Swagger documentation and / or add java docs
5. Implement 7th point - [Optional: Overlay the following data and re-compute] from original assignment
6. Dockerize the backend and frontend? Depends upon deployment strategy
7. Delete Excel files from the server onces Excel uploading is complete

## API Setup Guide

Refer [here](https://github.com/samarpanB/interstellar-travel/blob/master/interstellar-travel-api/README.md "here").

## UI Setup Guide

Refer [here](https://github.com/samarpanB/interstellar-travel/blob/master/interstellar-travel-ui/README.md "here").
