# Proposta do Projeto — DevPulse

## 1. Visão do Produto

Para desenvolvedores em formação que buscam contribuir com projetos open source,
Que precisam navegar por centenas de repositórios para achar tarefas de nível inicial,
O DevPulse é uma API de agregação de oportunidades open source
Que coleta, filtra e organiza issues por linguagem e nível de dificuldade,
Diferente da busca manual e dispersa na web,
o produto consolida vagas em um só lugar e responde buscas.

## 2. Definição do MVP

- **Dentro do MVP:** CRUD de tecnologias e repositórios favoritos; coletor concorrente em Go; busca paginada com cache; autenticação via JWT.
- **Fora do MVP:** Notificações automáticas via Discord/e-mail; recomendação por IA; área gráfica web; integração de submissão direta de Pull Requests.
- **Hipótese de Valor:** Acreditamos que estudantes de tecnologia utilizarão a API do DevPulse porque ela reduz o tempo de busca por tarefas acessíveis e responde em milissegundos.

## 3. Link para o Backlog

[Backlog no GitHub Projects] https://github.com/users/Gustavobiz/projects/2

## 4. Entidades Principais do Domínio

- **Issue:** ID, título, URL, linguagem, nível, status.
- **Repositorio:** ID, nome, URL, linguagem_principal, ativo.
- **Usuario:** ID, nome, e-mail, tecnologias_interesse.

## 5. Decisão da Stack Principal: Java com Quarkus

Optei por Java com Quarkus pelo baixo consumo de memória (RSS idle), rápido tempo de inicialização, ecossistema maduro e suporte nativo a especificações como OpenAPI e CDI.

## 6. Divisão de Responsabilidades com Go

- **Serviço Principal (Quarkus):** Regras de negócio, autenticação, persistência e rotas HTTP públicas.
- **Serviço Go:** Varredura concorrente de I/O e consultas periódicas de repositórios em plano de fundo.
- **Comunicação Interna:** gRPC utilizando contratos ProtoBuffers.

## 7. Equipe

- Gustavo Sousa Bernardes - Matrícula: 20260072831 - Desenvolvedor Solo

## 8. Coorte de Apresentação e Integração

- **Coorte:** Turma 01 - Segunda / Quarta.
- **Integração:** Projeto individual sem integração externa.
