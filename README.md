# demo-test

### REQUIRED TWITTER APP INSTALLED TO SIGNIN WITH TWITTER SDK ###

Setup the project in Android studio and run tests.

Download the project 

##git clone git@github.com:asielgil88/demo-test.git##

Import Project to Android Studio

The application code is located in src/main/java
Unit Tests are in src/test/java
Create a test configuration with the JUnit4 runner: org.junit.runners.JUnit4
Open Run menu | Edit Configurations
Add a new JUnit configuration
Choose module app
Run the newly created configuration
The unit test will be ran automatically.

Use Gradle from terminal.

Unit Test
```
./gradlew test
```
Instrumented Test (Required Device or Emulator connected to adb)
```
./gradlew connectedAndroidTest -i
```
See the report.
A report in HTML format is generated in app/build/reports/tests
