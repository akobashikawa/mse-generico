# mse Genérico - Monolito

- API REST con Spring Boot
- Módulos
    - Alfa
    - Beta
    - Gamma

```sh
curl http://localhost:8080/api/alfa

curl -X POST http://localhost:8080/api/alfa -H "Content-Type: application/json" -d '{"texto": "Hola Mundo", "entero":
 123, "decimal": 12.3}'
```