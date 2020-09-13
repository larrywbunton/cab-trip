# Cab Trip Cient
This runs in a terminal. It has 2 operations which are controlled by command line argumant.
- clear cache makes GET call to the server API and outputs the value
- cab-trip-count
  -  Reads in an input csv file
  -  Calls POST API to get the trip count for the cabs and dates provided.
  -  Outputs the value.

## Build with Maven
mvn clean package

This produces the /Target/ctclient.jar

## run
This will show the usage
jar -jar ctclient.jar 

## /run-test/rc.bat
This contains a number of runs with different command line arguments. It also contains a couple of csv files to test the program with.

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

