# mse Genérico - Monolito

- API REST con Spring Boot
- Módulos
    - Alfa
    - Beta
    - Gamma

```sh
# alfa
curl http://localhost:8080/api/alfa

curl -X POST http://localhost:8080/api/alfa -H "Content-Type: application/json" -d '{"texto": "A001", "entero":
 1, "decimal": 1.25}'

# beta
curl http://localhost:8080/api/beta

curl -X POST http://localhost:8080/api/beta -H "Content-Type: application/json" -d '{"texto": "B001", "entero":
 1, "decimal": 1.25}'
 
 # gamma
 curl http://localhost:8080/api/gamma
 
 curl -X POST http://localhost:8080/api/gamma -H "Content-Type: application/json" -d '{"id":0,"texto":"G001","entero":1,"decimal":1.25,"alfa":{"id":1},"beta":{"id":1}}'
```