# Proposta do Projeto — DevPulse

## 1. Visão do Produto
Para desenvolvedores em formação que buscam contribuir com projetos open source,
Que precisam navegar por centenas de repositórios para achar tarefas de nível inicial,
O DevPulse é uma API de agregação de oportunidades open source
Que coleta, filtra e organiza issues por linguagem e nível de dificuldade,
Diferente da busca manual e dispersa na web,
Nosso produto consolida vagas em um só lugar e responde buscas em milissegundos.

## 2. Definição do MVP
* **Dentro do MVP:** CRUD de tecnologias e repositórios favoritos; coletor concorrente em Go; busca paginada com cache; autenticação via JWT[cite: 1, 3, 4].
* **Fora do MVP:** Notificações automáticas via Discord/e-mail; recomendação por IA; área gráfica web; integração de submissão direta de Pull Requests[cite: 4].
* **Hipótese de Valor:** Acreditamos que estudantes de tecnologia utilizarão a API do DevPulse porque ela reduz o tempo de busca por tarefas acessíveis e responde em milissegundos[cite: 4].

## 3. Link para o Backlog
[Backlog no GitHub Projects](https://github.com/GustavoSousaBernardes/devpulse/projects/1)

## 4. Entidades Principais do Domínio
* **Issue:** ID, título, URL, linguagem, nível, status.
* **Repositorio:** ID, nome, URL, linguagem_principal, ativo.
* **Usuario:** ID, nome, e-mail, tecnologias_interesse.

## 5. Decisão da Stack Principal: Java com Quarkus
Optamos por Java com Quarkus pelo baixo consumo de memória (RSS idle), rápido tempo de inicialização, ecossistema maduro e suporte nativo a especificações como OpenAPI e CDI[cite: 1].

## 6. Divisão de Responsabilidades com Go
* **Serviço Principal (Quarkus):** Regras de negócio, autenticação, persistência e rotas HTTP públicas[cite: 1, 3, 4].
* **Serviço Go:** Varredura concorrente de I/O e consultas periódicas de repositórios em plano de fundo[cite: 1, 3, 4].
* **Comunicação Interna:** gRPC utilizando contratos ProtoBuffers[cite: 1, 3].

## 7. Equipe
* Gustavo Sousa Bernardes - Matrícula: 202X0000000 - Desenvolvedor Solo[cite: 4]

## 8. Coorte de Apresentação e Integração
* **Coorte:** Turma 01 - Segunda / Quarta[cite: 1, 4].
* **Integração:** Projeto individual sem integração externa[cite: 4].
