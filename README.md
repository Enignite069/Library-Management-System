# Library Management System
## Overview
A Back-end API Project, can be used as back-end system or api testing.\
This system is made by:
- Spring Boot 3.3.2
- Maven Project
- Java 21
- MySQL Database
## How to run
* Step 1: Refresh Maven Project to install all dependencies of the project
* Step 2: Config the application to run in file application.yml (database port,...)
![Application Config](https://github.com/Enignite069/Library-Management-System/blob/main/set%20up/ConfigApplication.png)
* Step 3: Create blank database with the following name in databsourse url path (In this project: **library_management**).
* Step 4: Run the application and the API document will appear in http://localhost:8080/library/swagger-ui/index.html
## In App Running
Most of the function will be locked and need to authorize to unlock. The default admin account will be created at the first time
system has run. This accout can be found at file **/configuration/identity/ApplicationInitConfig.java**\. You can change this to whatever you want
![Default Account](https://github.com/Enignite069/Library-Management-System/blob/main/set%20up/DefaultAdminAccount.png)
