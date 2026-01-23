# 🛒 IF Market - Back-end

Esse é o repositório da parte back‑end do projeto acadêmico da disciplina DAC (Desenvolvimento de Aplicações Corporativas), onde está implementada toda a lógica de negócios seguindo os princípios de arquitetura corporativa e boas práticas de desenvolvimento.
O sistema foi construído em Spring Boot, utilizando Spring Security para autenticação e autorização via JWT, garantindo acesso seguro aos endpoints conforme os perfis de usuário (ADMINISTRADOR, ALUNO e PROFESSOR).
---
## Sobre o IF Market 🛒

- **É um marketplace acadêmico para compra e venda de itens entre alunos e professores.**
- **Possui gerenciamento de usuários, itens, categorias, fotos, notificações e propostas.**
- **Implementa fluxo de autenticação com JWT e redefinição de senha via email.**

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (Web, Validation, Security)
- **Spring Data JPA**
- **Hibernate**
- **Banco de Dados**: PostgreSQL
- **Lombok**
- **Maven**

---

## 📂 Estrutura do Projeto
```
src
  └── main
        ├── java
        │     └── io
        │          └── github
        │                   └── devcaioalves
        │                                  └── projetodacbackifmarket
        │                                                 ├── client
        |                                                 ├── config
        │                                                 ├── controllers
        │                                                 ├── docs
        │                                                 ├── dto
        │                                                 ├── entities
        │                                                 ├── exceptions
        |                                                 ├── jwt
        │                                                 ├── repositories
        │                                                 ├── service
        │                                                 └── ProjetodacBackIfmarketApplication.java
        └── resources
            └── application.properties
```

---

## ⚙️ Configuração e Execução

### Pré-requisitos
- **Java 17+**
- **Maven 3.8+**
- Banco de dados configurado (ver `application.properties`)

### Clonar o repositório
```bash
git clone https://github.com/devcaioalves/projeto_dac_back_IFMarket.git
cd projeto_dac_back_IFMarket
```

---
## Rodar a aplicação
```bash
mvn spring-boot:run
```
---
## 📡 Endpoints Principais

### 👤 Usuários

#### Tabela End-point
| Método | Endpoint                                                | Descrição                  |
|--------|---------------------------------------------------------|----------------------------|
| POST   | /br/com/ifmarket/usermanagent/v1/criar-usuario          | Criar novo usuário         |
| POST   | /br/com/ifmarket/usermanagent/v1/login                  | Login e geração de JWT     |
| GET    | /br/com/ifmarket/usermanagent/v1/buscar-usuario/{id}    | Buscar usuário por ID      |
| PUT	   | /br/com/ifmarket/usermanagent/v1/atualizar-usuario/{id} | Atualizar dados do usuário |
| DELETE | /br/com/ifmarket/usermanagent/v1/deleta-usuario/{id}    | Apaga um usuário           |

### 📦 Itens

#### Tabela End-point
| Método | Endpoint                                             | Descrição               |
|--------|------------------------------------------------------|-------------------------|
| POST   | /br/com/ifmarket/itemmanagent/v1/criar-item          | Criar item              |
| GET    | /br/com/ifmarket/itemmanagent/v1/buscar-todos-itens  | Listar todos os itens   |
| PUT	   | /br/com/ifmarket/itemmanagent/v1/atualizar-item/{id} | Atualizar dados do item |
| DELETE | /br/com/ifmarket/itemmanagent/v1/deleta-item/{id}    | Apaga um item           |

### 🏷️ Categorias

#### Tabela End-point
| Método | Endpoint                                                        | Descrição                    |
|--------|-----------------------------------------------------------------|------------------------------|
| POST   | /br/com/ifmarket/categoriamanagent/v1/criar-categoria           | Criar categoria              |
| GET    | /br/com/ifmarket/categoriamanagent/v1/buscar-todas-categorias   | Listar todas as categorias   |
| PUT	   | /br/com/ifmarket/categoriamanagent/v1/atualizar-categoria/{id}  | Atualizar dados da categoria |
| DELETE | /br/com/ifmarket/categoriamanagent/v1/deleta-categoria/{id}     | Apaga uma categoria          |

Entre outros end-points ...
---
## ✍️ Autores
> por Caio da Silva Alves & Livia Gonçalves de Freitas.

