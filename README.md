# Marketplace Rest Api

Market Place is an application where people can register, upload their products and sell them. Also the app provides information about products, sellers, sales and current stock. This project provides the backend part of the application.

## Prequisites
In order to run locally, the following needs to be installed:
- [Gradle](https://gradle.org/install/)
- [MySQL](https://dev.mysql.com/doc/mysql-getting-started/en/#mysql-getting-started-installing)

## Setting up the environment
1. Create a database with the help of MySQL
```
mysql> CREATE DATABASE DATABASE_NAME;
```
2. Import test database
```
mysql> use DATABASE_NAME;

mysql> SET autocommit=0 ; source marketplace_db.sql ; COMMIT ;
```
3. Set up the following environmental variables (or edit the env_variable.sh file and execute it `. ./env_variable.sh`):
- DATABASE_NAME: name of the database
- DATABASE_URL: url of the database (`DATABASE_URL="jdbc:mysql://localhost/${DATABASE_NAME}?useSSL=false"`)
- DATABASE_USERNAME: username of the database
- DATABASE_PASSWORD: password of the database

## Run
Inside the folder of the project, execute command:
```
./gradlew run
```

## Test
- Running rest endpoint unit test:
```
./gradlew test
```
- Calling endpoints manually from Postman: 
  - Import Collection from file `marketplace-rest-api.postman_collection.json`
  - Edit variables from pre-request script for some **GET/DELETE** method and body from some **POST/PUT** method
  
## API Interface
- Swagger API interface can be accessed at:
`http://localhost:8080/marketplace/api/swagger-ui.html`
