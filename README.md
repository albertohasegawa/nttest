# Technical Exercise 

This application is an example of a bank application for saving scheduled transactions from one user to another.

Before you begin, ensure you have the following installed:

- Java 11 or later
- Docker
- Git
- Maven
- Postman

## Setup

### Cloning the Repository
```bash
 git clone git@github.com:albertohasegawa/nttest.git
```

### Setting up local database in MySQL
```
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=rootpw -e MYSQL_DATABASE=nttest -e MYSQL_USER=user -e MYSQL_PASSWORD=pass -p 3306:3306 -d mysql:latest
```

### Install Maven Dependencies and running application
```
mvn clean install
mvn spring-boot:run
```

### Creating new customer
In postman, create a request to the customer endpoint as following:
`POST http://localhost:8080/customer`
```
{
    "username": "username",
    "password": "password",
    "name": "Your name"
}
```
### Creating new transaction
In postman, create a request to the transaction endpoint as following:
`POST http://localhost:8080/transaction`
```
{
    "amount": 1000.0,
    "sender": "user1",
    "receiver": "user2",
    "scheduledDate": "2027-07-10",
    "transferDate": "2027-07-10"
}
```
### Updating transaction
`PUT http://localhost:8080/transaction/{id}`
```
{
    "amount": 1000.0,
    "sender": "user1",
    "receiver": "user2",
    "scheduledDate": "2027-07-10",
    "transferDate": "2027-07-10"
}
```
### Deleting transaction
`DELETE http://localhost:8080/transaction/{id}`
### Find by transaction id
`GET http://localhost:8080/transaction/{id}`
### Find all transactions
`GET http://localhost:8080/transaction`
