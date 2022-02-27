# Rest Services with Spring Boot

Start server
```
./mvnw clean spring-boot:run
```

Query all
```
curl -v localhost:8080/employees
```
Query record 1
```
curl -v localhost:8080/employees/1
```
Create entry
```
curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
```
Update entry
```
curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "companion"}'
```
Delete entry
```
curl -X DELETE localhost:8080/employees/3
```
