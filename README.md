# DevPulse

O **DevPulse** é um ecossistema de microsserviços voltado para o monitoramento, conciliação e busca de métricas/dados. Projeto desenvolvido para a disciplina **WEB2** na UFRN.

---

## Arquitetura e Tecnologias

O projeto é estruturado em formato de **monorepo**:

- **`services/api-quarkus`**: Serviço principal em **Java 21** utilizando **Quarkus Framework** para alta performance e APIs REST.
- **`services/busca`**: Microsserviço de busca otimizada desenvolvido em **Go**.
- **`services/conciliacao`**: Microsserviço de processamento e conciliação de dados desenvolvido em **Go**.

---

## Pré-requisitos

Para rodar e construir a aplicação localmente, você precisará de:

- **Java JDK 21**
- **Go 1.22+**
- **Docker & Docker Compose**
- **Mise**

---

## Como Executar

### 1. Usando o Docker Compose (Ambiente completo)

Para subir todos os microsserviços em contêineres:

```bash
docker-compose up --build
```
