# desapp-groupL-backend  

## Eventeando backend  

### Tecnologías utilizadas  
Se desarrollo una aplicación utilizando Java 8, Spring Boot y Jersey. La misma otorga servicios via API en http://localhost:8080/.

__Servicios API__:

| Method |             URL            |                                Request                                |
|:------:|:--------------------------:|:----------------------------------------------------------------------:|
|  POST  |     event/createParty      | {"title": "title", "owner": owner, "expirationDate": [yyyy,mm,dd], "date": [yyyy,mm,dd], "guests": guests, "items": items} |
|  POST  |     user/create            | {"name": "name", "lastName": "lastName", "email": "email", "password": "password", "birthDate": [yyyy,mm,dd]} |
