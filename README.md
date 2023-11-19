# Home task

## Requirements
Both the frontend and backend are in the project.

Due to Angular being used, ``node`` needs to be installed on the local machine to run the project

## Setup
The project contains a docker-compose file that creates the database which will hold the data

## Run
The application can be run either locally (this requires the profile "localhost" to be used), or with the Dockerfile in 
the project

On startup, the database will be filled with data from database migration scripts

In case of running it locally, the frontend can be built with the `buildFrontend` task in gradle.
This task builds the webapp, and copies the files into the static directory of the SpringBoot app.

If the application is being run with Docker, the container needs to be published on port `8080`.

## Tests
The integration tests use `testcontainer` to provide database functionality. This means that docker needs to run when 
the integration test are executed

