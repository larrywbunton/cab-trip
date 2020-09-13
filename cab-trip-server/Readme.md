# Cab Trip Server
- This demo program uses Spring Boot. 
- I used the spring boot initialzr to is a self-contained Jar with embedded web server.
- Exposes 2 Rest APIs
- Connects to a mySql database
- Caches the database query

## Setup
- update /cab-trip-server/src/main/resources/application.properties with MySql user details.

## To Build and Run
mvn clean package

The executable jar file ./target/ctserver-0.0.1-SNAPSHOT.jar

java -jar ctserver-0.0.1-SNAPSHOT.jar

### Run
/dev-test-scripts/rs.bat

### Test with Postman
The following files that are sent separately are required to run.
- /dev-test-scripts/cabtrip-server.postman_collection.json

### Maven Settings
.mvn blank repo settings file.

### Checkstyle
The checkstyle.xml contains the setting.

#### Intellij IDE Setup Notes
For Intellij to use the checkstyle you will need to get the CheckStyle-IDE plugin by Jamie Sheil

Filter setting on checkstyle

Tools > Checkstyle -> Configuration Files Table press +

Add a local configuration file with a label **cab-trip-server checkStyle** for the **/checkstyle.xml** file.
Check the active column.

#### Running mvn with checkstyle
mvn clean package checkstyle:check
