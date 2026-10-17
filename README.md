# DevPulse

O **DevPulse** é um ecossistema de microsserviços voltado para o monitoramento, conciliação e busca de métricas/dados. Projeto desenvolvido para a disciplina de Engenharia de Software / Desenvolvimento Web (DIM0547) na UFRN.

---

## Arquitetura e Tecnologias

O projeto é estruturado em formato de **monorepo**:

- **`services/api-quarkus`**: Serviço principal em **Java 21**, utilizando **Quarkus Framework** para alta performance e APIs REST. Implementado seguindo os princípios de **Clean Architecture** (separação de Domínio, Adaptadores e Recursos HTTP).
- **`services/busca`**: Microsserviço de busca otimizada desenvolvido em **Go 1.22**.
- **`services/conciliacao`**: Microsserviço de processamento e conciliação de dados desenvolvido em **Go 1.22**.
- **`banco`**: Banco de dados relacional **PostgreSQL 17** para persistência, com gerenciamento de migrações automáticas via **Flyway**.

---

## Pré-requisitos

Para rodar e construir a aplicação localmente, você precisará de:

- **Java JDK 21**
- **Go 1.22+**
- **Docker & Docker Compose**
- **Mise** (gerenciador de tarefas e ambiente)

---

## Como Executar

### 1. Usando o Mise (Recomendado)

O projeto utiliza o `mise` para simplificar a gestão de tarefas e comandos do ciclo de vida da aplicação.

**Subir o ambiente completo (PostgreSQL + Microsserviços):**

```bash
mise run up
```

**Compilar todos os serviços:**

```bash
mise run build
```

**Executar a suíte de testes:**

```bash
mise run test
```

**Desligar o ambiente:**

```bash
mise run down
```

**Limpar volumes e dados do banco:**

```bash
mise run clean
```

### 2. Usando o Docker Compose (Ambiente completo)

Para subir todos os microsserviços e o banco de dados em contêineres:

```bash
docker compose up -d --build
```

Para verificar o status e a saúde dos contêineres:

```bash
docker compose ps
```

### 3. Executando a API Quarkus em Modo Desenvolvimento (Live Coding)

Caso queira rodar o banco no Docker e a API Quarkus localmente com suporte a _hot reload_:

1. Suba apenas o banco de dados PostgreSQL:

```bash
docker compose up -d banco
```

2. Acesse o diretório do serviço Quarkus e inicie em modo `dev`:

```bash
cd services/api-quarkus
./mvnw quarkus:dev
```

A API estará acessível localmente em `http://localhost:8080`.

---

## Documentação da API (OpenAPI / Swagger)

A API possui documentação interativa gerada automaticamente via SmallRye OpenAPI e Swagger UI.

- **Swagger UI Interativo:** [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)
- **Especificação OpenAPI (YAML/JSON):** [http://localhost:8080/q/openapi](http://localhost:8080/q/openapi)

---

## Funcionalidades Principais (Sprint 1)

### CRUD de Fontes de Integração (`/fontes`)

- Listagem com paginação (`pagina`, `tamanho`) e filtro por nome (`nome`).
- Busca por ID.
- Criação com retorno `201 Created` e cabeçalho `Location`.
- Atualização e remoção com retorno `204 No Content` para operações sem conteúdo.

### CRUD de Issues Aninhadas (`/fontes/{fonteId}/issues`)

- Listagem de tarefas vinculadas a uma fonte com filtro por estado (`estado`).
- Validação de existência da fonte pai antes de realizar qualquer operação.

### Tratamento de Erros Padronizado (RFC 9457 — Problem Details)

- `404 Not Found` para recursos inexistentes.
- `422 Unprocessable Entity` para falhas de validação no corpo da requisição (`Bean Validation`).
- `409 Conflict` para violações de integridade relacional ou chaves únicas no banco.
- Formato de resposta padronizado em `application/problem+json`.

### link video:

https://drive.google.com/drive/folders/1MI7Gh15qZqL2rOEKyB1uKyapj6PN__5g?usp=sharing
