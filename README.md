# Rest Services with Spring Boot

Start server
```
./mvnw clean spring-boot:run
```

Query all
```
curl -v localhost:8080/employees
curl -v http://localhost:8080/orders
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
curl -v -X DELETE http://localhost:8080/orders/4/cancel
```

Lessons from the tutorial:
```
Don’t remove old fields. Instead, support them.
Use rel-based links so clients don’t have to hard code URIs.
Retain old links as long as possible. Even if you have to change the URI, keep the rels so older clients have a path onto the newer features.
Use links, not payload data, to instruct clients when various state-driving operations are available.
```
