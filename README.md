# TestTaskFebruary2023


## Table of Contents
* [Technical task](#technical-task)
* [Prerequisites](#prerequisites)
* [Resolve dependencies](#resolve-dependencies)
* [Build and run tests](#build-and-run-tests)
* [Project structure and tests](#project-structure-and-tests)

## Technical task
https://github.com/Hipo/university-domains-list

Using the public api from the “2 - Using The Hosted API” section, prepare 5 tests that check the GET api for getting universities.
Write about the choice of each of the created test cases (based on test design theory).
As a framework API please use Rest-Assured
You can use other frameworks as you wish.
Use the file filtered_world_universities_and_domains.json, which is in the repository of this project,
as a store and to check the data in the output during the testing. As a result, we expect:
1) Link to the repository with tests
2) Description of how to run tests locally
3) A small document explaining the selection of each of the test cases

## Prerequisites
To build this project and run tests you should have installed on your system **maven** (3.5 and later)

Java (1.8 and later): Folder with java executable file should be added to PATH environment variable.

## Resolve dependencies
Open terminal, and go to project folder. Finally terminal should be opened in folder with pom.xml file.
Run
```sh
$ mvn dependency:resolve
```
Wait until all libraries mentioned in '<dependencies>' section of pom.xml project configuration file and all other
required libraries will be downloaded from remote maven repository to local one.

## Build and run tests
In the same terminal session run tests
```sh
$ mvn test
```
Wait while project is built, and tests run. At the end of this process you'll see tests run results.


## Project structure and tests
Project contains several classes. Here wil be described two of them:
1. com.hipolabs.universities.search.**SearchUniversities**
2. com.hipolabs.universities.tests.**SearchUniversitiesTests**

Class **SearchUniversities** contains methods to perform requests to **"/search"** endpoint with or without parameters
(i.e. **"name"** and **"country"**). Methods of this class were used it tests.

Class **SearchUniversitiesTests** contains 5 parameterized tests methods, that perform next tests:
1. Request universities search by country name parameter only. Parameter's case should be ignored. 
E.g. GET **"/search?country=Lithuania"** and **"/search?country=LITHUANIA"** should successfully return non-empty list
of all universities in Lithuania. List of actual universities should contain the same or more universities than in 
**world_universities_and_domains.json** file. The purpose of this test is to ensure that the API is able to handle 
queries with parameters correctly and return the expected data.
2. Request universities by university name parameter only. Parameter's case should be ignored.
E.g. GET **"/search?name=Kaunas Medical Academy"** and **"/search?name=KAUNAS MEDICAL ACADEMY"** should successfully 
return same information about university. Only one university should be found in results. The purpose of this test is to 
ensure that the API is able to handle queries with parameters correctly and return the expected data.
3. Request universities by partial university name parameter only. Parameter's case should be ignored.
E.g. GET **"/search?name=Kaunas"** and **"/search?name=KAUNAS"** should successfully return non-empty list of all 
universities with word "Kaunas" in their names. List of actual universities should contain the same or more universities 
than in **world_universities_and_domains.json** file. The purpose of this test is to ensure that the API is able to 
handle queries with partial **name** parameter correctly and return the expected data.
4. Negative test. Request universities by incorrect pairs of country and university name. All pairs on **name** and 
**country** should return empty lists of universities. E.g. request for correct university name + existing country name 
(but where this university is not exist) should return empty response. The purpose of this test is to ensure that the 
API handles incorrect queries and does not return any data.
5. Negative test. Send POST request to **"/search"** endpoint. **HTTP 405** response should be returned. The purpose of this 
test is to ensure that the API correctly handles unsupported request methods and correctly returns response status and 
error message. 