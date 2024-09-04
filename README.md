#### Coding Assignment
I have implemented this in JAVA.
Tech stack : Java Spring boot, MySQL, Jackson package to serialize or deserialize JSON object to `QuerRequest` model.
Scope of work:
1. converting JSON into `QueryRequest` model 
2. Genrating SQL query.
3. executing SQL query using JDBC and converting resutset into `QueryResponce`

#### Code flow
There is three package  
1. com.hasura.query.model (which contains all the model file requrd to deserialize `Queryrequest` JSON payload)
2. com.hasura.query.engine (In this package `QueryGeneratorApplication` is main class of Spring Boot other two class is to create JDBC connection and executing query)
3. com.hasura.query.controller (`QueryController` has one post method we can use this from `postman` to send `QueryRequest` json payload and get `QueryResponce` as JSOn object)
4. There is class called `SQLGenerator` This class is responsible for converting JSON into `QueryReqest` Object. and then generating a SQL query which used for execution. 

#### How to build the project JAR
run this command in terminal
`NOTE:` Before running setup Mysql and create database,table and insert record using `chinook.sql` file.
1. `mvn clean package`  (it will generate target folder)
2. `java -jar target\query-generator-0.0.1-SNAPSHOT.jar` it will run the jar at 8080 port
3. use postman as POST call http://localhost:8080/api/execute it will only except JSON payload.

**Limitation:**

I have tested entire flow with given query, you can refer the same project.
`enterprise-connector-exercise\queries` 
There are lot of edge casese which I have not coverd because of the scope ristriction.
But thoes also can be handle.


