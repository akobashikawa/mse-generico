# mse Genérico - Monolito

- API REST con Spring Boot
- Módulos
    - Alfa
    - Beta
    - Gamma

```sh
# alfa
curl http://localhost:8080/api/alfa

curl -X POST http://localhost:8080/api/alfa -H "Content-Type: application/json" -d '{"texto": "Hola Alfa", "entero":
 123, "decimal": 12.3}'

# beta
curl http://localhost:8080/api/beta

curl -X POST http://localhost:8080/api/beta -H "Content-Type: application/json" -d '{"texto": "Hola Beta", "entero":
 123, "decimal": 12.3}'
```