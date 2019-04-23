# desapp-groupL-backend  

## Eventeando backend  

### Tecnologías utilizadas  
Se desarrollo una aplicación utilizando Java 8, Spring Boot y Jersey. La misma otorga servicios via [API](http://localhost:8080/api).

__Servicios API__:

| Method |             URL            |                                Request                                |
|:------:|:--------------------------:|:----------------------------------------------------------------------:|
|  POST  | event/createParty | {"title": "title", "owner": owner, "expirationDate": expirationDate, "date": date, "guests": guests, "items": items} |
|  POST  | user/create | {"name": "name", "lastName": "lastName", "email": "email", "password": "password", "birthDate": birthDate} |
