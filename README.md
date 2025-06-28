# approval-service

Esse projeto tem como objetivo representar uma arquitetura de microserviços, utilizando um IDP e MQTT como mediador de mensagens async atraves dos microsserviços.

Para a execução completa, executar em paralelo o projeto:

> https://github.com/daviemediato/expense-service

Como pré requisito, é necessário que o MQTT esteja rodando na porta 1883.
É necessário subir as imagens pelo docker compose através de um dos projetos.
**Obs: A imagem do postgresql pode ser iniciado em seu respectivo projeto, ou em um único docker-compose.**
**Importante conferir se a porta de configuração para a conexão com o datasource está correto no application.yml**

```YML
services:
  keycloak:
    image: quay.io/keycloak/keycloak:latest
    container_name: keycloak
    command: "start-dev"
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    ports:
      - "8080:8080"

  mqtt:
    image: emqx/emqx:latest
    environment:
      - "EMQX_LISTENERS_TCP_DEFAULT_BIND=1884"
      - "EMQX_NAME=MQTT"
      - "EMQX_HOST=localhost"
    ports:
      - "1883:1883"
      - "1884:1884"
      - "18083:18083"
```
