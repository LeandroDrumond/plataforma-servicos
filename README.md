# 🛠️ Plataforma de Serviços

## 📌 Objetivo
Conectar **empresas**, **clientes** e **prestadores de serviços** em um único ambiente digital, facilitando a contratação, gestão e execução de serviços diversos.  
O sistema garante transparência, segurança e praticidade, padronizando o fluxo desde a solicitação do serviço até a conclusão da ordem.

## 🚀 Funcionalidades
- Cadastro de **empresas**, **clientes** e **prestadores de serviços**.  
- Prestadores informam suas **áreas de atuação** (ex.: elétrica, jardinagem, limpeza de piscinas, montagem de móveis, faxina etc).  
- Clientes solicitam à empresa o tipo de serviço desejado e a **data de atendimento**.  
- A empresa localiza no sistema um **prestador qualificado e disponível**.  
- O prestador recebe a proposta, **define o valor do serviço** e aceita a ordem.  
- A ordem de serviço segue um fluxo de **status**:  
  - `CRIADA` → `ACEITA` → `EM_ANDAMENTO` → `CONCLUIDA` ou `CANCELADA`.  
- O prestador recebe **90% do valor da ordem**, e a empresa fica com **10% como taxa de intermediação**.  

## 🏗️ Arquitetura e Padrões
O projeto segue boas práticas de desenvolvimento com **Spring Boot + JPA**, aplicando o padrão **Camadas (Layered Architecture)**:

- **Controller** → expõe endpoints REST (API).  
- **Service** → contém regras de negócio.  
- **Repository** → abstrai o acesso ao banco de dados via **Spring Data JPA**.  
- **Domain/Model** → mapeamento das entidades (JPA Entities).  
- **Exceptions** → tratadas de forma padronizada via **GlobalExceptionHandler**.

## ⚙️ Tecnologias Utilizadas
- **Java 17**  
- **Spring Boot 3** (Web, JPA, Validation)  
- **Hibernate** (implementação JPA)  
- **H2 Database** (ambiente de desenvolvimento e testes)  
- **Maven** (gerenciamento de dependências)  
- **REST API** com suporte a `GET`, `POST`, `PUT`, `PATCH` e `DELETE`  
- **Exception Handling** customizado com `@RestControllerAdvice`  

