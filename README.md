# ThinDesk — Sistema de Help Desk com MongoDB

Sistema web desenvolvido com **Java e Spring Boot**, utilizando **MongoDB** como banco de dados, **Thymeleaf** para renderização das páginas e **Spring Security** para autenticação e controle de acesso.

O projeto possui funcionalidades para gerenciamento de usuários, chamados, clientes e horários de atendimento, com diferentes níveis de acesso: **Usuário, Gerente e Administrador**.

---

## Sobre o projeto

O **ThinDesk** foi desenvolvido com o objetivo de centralizar o atendimento de solicitações de suporte em uma aplicação web.

A aplicação permite:

- Cadastro e autenticação de usuários;
- Controle de acesso baseado em perfil;
- Gerenciamento de chamados;
- Gerenciamento de clientes;
- Configuração de horários de atendimento;
- Painéis específicos para usuários, gerentes e administradores;
- Persistência dos dados no MongoDB;
- Persistência das sessões HTTP no MongoDB;
- Validação dos dados de cadastro;
- Criptografia de senhas utilizando BCrypt;
- Interface web utilizando Thymeleaf, HTML, CSS e JavaScript.

---


## Objetivo

O ThinDesk busca fornecer uma base de sistema de atendimento técnico com autenticação, autorização, gerenciamento de chamados e persistência em MongoDB, servindo também como projeto de estudo para desenvolvimento web com **Spring Boot, Spring Security, MongoDB e Thymeleaf**.

---

## Tecnologias utilizadas

### Backend

| Tecnologia | Versão/Configuração |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.0 |
| Spring Web | 3.4.0 |
| Spring Data MongoDB | 3.4.0 |
| Spring Security | 3.4.0 |
| Spring Session Data MongoDB | 3.4.0 |
| Spring Validation | 3.4.0 |
| Thymeleaf | Spring Boot 3.4.0 |
| Thymeleaf Layout Dialect | 3.3.0 |
| Thymeleaf Extras Spring Security 6 | 3.1.2.RELEASE |
| Lombok | 1.18.36 |
| Maven | Wrapper incluído no projeto |
| MongoDB | MongoDB Atlas / MongoDB compatível |

### Frontend

- HTML5
- CSS3
- JavaScript
- Thymeleaf
- Thymeleaf Layout Dialect

### Banco de dados

- MongoDB
- MongoDB Atlas
- Spring Data MongoDB
- Spring Session com MongoDB

---

## Arquitetura

O projeto segue uma organização em camadas, separando responsabilidades entre controladores, serviços, repositórios, entidades/modelos e configurações.

```text
ThindeskApplication
        │
        ▼
   Controllers
        │
        ▼
     Services
        │
        ▼
   Repositories
        │
        ▼
     MongoDB
```

A camada de apresentação utiliza páginas Thymeleaf:

```text
Navegador
    │
    ▼
Spring MVC / Controllers
    │
    ├── Thymeleaf
    │
    ▼
HTML + CSS + JavaScript
```

---

## Estrutura do projeto

```text
thindesk/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── pfc/
│       │           └── thindesk/
│       │               ├── config/
│       │               │   ├── MongoInitConfig.java
│       │               │   ├── MongoSessionConfig.java
│       │               │   └── SecurityConfig.java
│       │               │
│       │               ├── controller/
│       │               │   ├── ChamadoController.java
│       │               │   ├── ClienteController.java
│       │               │   ├── ControllerAutenticacao.java
│       │               │   ├── ControllerUsuario.java
│       │               │   ├── HomeController.java
│       │               │   └── HorarioAtendimentoController.java
│       │               │
│       │               ├── dto/
│       │               │   ├── CadastroUsuarioDTO.java
│       │               │   └── LoginDTO.java
│       │               │
│       │               ├── entity/
│       │               │   ├── Chamado.java
│       │               │   ├── Cliente.java
│       │               │   └── HorarioAtendimento.java
│       │               │
│       │               ├── model/
│       │               │   ├── Perfil.java
│       │               │   └── Usuario.java
│       │               │
│       │               ├── repository/
│       │               │   ├── ChamadoRepository.java
│       │               │   ├── ClienteRepository.java
│       │               │   ├── HorarioAtendimentoRepository.java
│       │               │   └── RepositoryUsuario.java
│       │               │
│       │               ├── service/
│       │               │   ├── ChamadoService.java
│       │               │   ├── ClienteService.java
│       │               │   ├── HorarioAtendimentoService.java
│       │               │   ├── ServiceAutenticacao.java
│       │               │   └── ServiceUsuario.java
│       │               │
│       │               └── ThindeskApplication.java
│       │
│       └── resources/
│           ├── application.properties
│           ├── chamados.json
│           ├── static/
│           │   ├── css/
│           │   ├── images/
│           │   └── js/
│           └── templates/
│               ├── administrador/
│               ├── autenticacao/
│               ├── chamados/
│               ├── erro/
│               ├── error/
│               ├── fragmentos/
│               ├── gerente/
│               └── usuario/
│
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

# Autenticação e autorização

O sistema utiliza **Spring Security** para proteger as rotas e controlar o acesso conforme o perfil do usuário.

Os perfis disponíveis são:

```text
USUARIO
   │
   ▼
GERENTE
   │
   ▼
ADMINISTRADOR
```

### USUARIO

Usuários comuns podem acessar:

- Dashboard;
- Perfil;
- Chamados;
- Clientes.

### GERENTE

Além das funcionalidades de usuário, o gerente possui acesso a:

- Painel de gerente;
- Ajustes de horários de atendimento.

### ADMINISTRADOR

O administrador possui acesso às áreas administrativas, incluindo:

- Painel administrativo;
- Gerenciamento de usuários;
- Alteração de perfil dos usuários;
- API de clientes;
- API de chamados;
- Recursos disponíveis para gerentes e usuários.

---

# Cadastro de usuários

O cadastro utiliza `CadastroUsuarioDTO` e possui validações para:

- Nome obrigatório;
- Nome com pelo menos 3 caracteres;
- E-mail obrigatório;
- Validação do formato do e-mail;
- Senha obrigatória;
- Senha com pelo menos 8 caracteres;
- Confirmação de senha.

As senhas **não são armazenadas em texto puro**.

O sistema utiliza:

```text
BCryptPasswordEncoder
```

com fator de custo `12`.

Fluxo simplificado:

```text
Cadastro
   │
   ▼
Validação dos dados
   │
   ▼
Verificação de e-mail existente
   │
   ▼
Hash da senha com BCrypt
   │
   ▼
Criação do usuário
   │
   ▼
MongoDB
```

Novos usuários são cadastrados inicialmente com o perfil:

```text
USUARIO
```

---

# MongoDB

O projeto utiliza MongoDB como banco de dados principal.

As principais coleções utilizadas são:

```text
usuarios
chamados
clientes
horariosAtendimento
sessions
```

A aplicação também possui uma configuração de inicialização que verifica a existência das coleções principais e as cria quando necessário.

### Entidades

#### Usuario

```text
id
nome
email
senha
perfil
ativo
dataCriacao
dataAtualizacao
```

O e-mail possui índice único.

#### Chamado

```text
id
descricao
status
tipo
tecnico
usuario
```

#### Cliente

```text
id
nome
telefone
setor
```

#### HorarioAtendimento

```text
id
setor
diaSemana
horarioInicio
horarioFim
```

---

# Gerenciamento de chamados

Os chamados podem ser manipulados pela API:

```text
/api/chamados
```

### Criar chamado

```http
POST /api/chamados
```

### Listar chamados

```http
GET /api/chamados
```

### Atualizar chamado

```http
PUT /api/chamados/{id}
```

Também existe um fluxo baseado em formulário para atualização:

```http
POST /api/chamados/atualizar/{id}
```

---

# Gerenciamento de clientes

A API de clientes utiliza:

```text
/api/clientes
```

### Criar cliente

```http
POST /api/clientes
```

### Listar clientes

```http
GET /api/clientes
```

O acesso à API de clientes é restrito ao perfil:

```text
ADMINISTRADOR
```

---

# Horários de atendimento

O sistema possui gerenciamento dos horários de atendimento.

Funcionalidades disponíveis:

- Listar horários;
- Listar horários por setor;
- Salvar horário;
- Excluir horário.

Rotas relacionadas:

```text
/ajustes-horarios
/api/ajustes-horarios
/api/ajustes-horarios/salvar
/api/ajustes-horarios/deletar/{id}
```

O gerenciamento é protegido para usuários com perfil de **Gerente** ou **Administrador**.

---

# Administração de usuários

O painel administrativo está disponível em:

```text
/administrador/painel
```

Somente usuários com:

```text
ADMINISTRADOR
```

podem acessar essa área.

O administrador pode visualizar os usuários cadastrados e alterar seus perfis através da funcionalidade de promoção/alteração de perfil.

---

# DTOs

O projeto utiliza DTOs para separar os dados recebidos nos formulários das entidades persistidas.

### CadastroUsuarioDTO

Responsável pelos dados de cadastro:

```text
nome
email
senha
senhaConfirmacao
```

### LoginDTO

Representa os dados de autenticação:

```text
email
senha
```

---

# Fluxo de autenticação

O login utiliza formulário próprio integrado ao Spring Security.

Fluxo:

```text
Usuário
   │
   ▼
Tela de Login
   │
   ▼
POST /login
   │
   ▼
Spring Security
   │
   ▼
ServiceAutenticacao
   │
   ▼
RepositoryUsuario
   │
   ▼
MongoDB
   │
   ├── Credenciais válidas
   │        │
   │        ▼
   │     Dashboard
   │
   └── Credenciais inválidas
            │
            ▼
       Tela de Login
```

As sessões HTTP são persistidas no MongoDB utilizando Spring Session.

O tempo máximo de inatividade configurado é de:

```text
30 minutos
```

---

# Configuração do ambiente

## Pré-requisitos

Antes de executar o projeto, instale:

- Java JDK 21;
- MongoDB ou uma conta no MongoDB Atlas;
- Git;
- Maven (opcional, pois o projeto possui Maven Wrapper).

Verifique o Java:

```bash
java -version
```

O projeto foi configurado para compilação com:

```text
Java 21
```

---

# Configuração do MongoDB

A aplicação utiliza a variável de ambiente:

```text
MONGODB_URI
```

Exemplo:

```text
mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority
```

Também é possível definir o banco através da configuração:

```properties
spring.data.mongodb.database=thindesk
```

### Windows — PowerShell

```powershell
$env:MONGODB_URI="mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority"
```

### Windows — CMD

```cmd
set MONGODB_URI=mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority
```

### Linux/macOS

```bash
export MONGODB_URI="mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority"
```

> **Importante:** nunca publique credenciais reais do MongoDB no GitHub. Utilize variáveis de ambiente ou um arquivo de configuração local que não seja versionado.

---

# Usuário administrador

O Spring Security possui configurações padrão para:

```text
ADMIN_NAME
ADMIN_PASSWORD
```

Os valores podem ser definidos por variáveis de ambiente.

Exemplo:

### Windows PowerShell

```powershell
$env:ADMIN_NAME="admin"
$env:ADMIN_PASSWORD="sua-senha"
```

### Linux/macOS

```bash
export ADMIN_NAME="admin"
export ADMIN_PASSWORD="sua-senha"
```

> Altere as credenciais padrão antes de utilizar o sistema em um ambiente real.

---

# Executando o projeto

Entre na pasta do projeto:

```bash
cd thindesk
```

## Windows

Utilizando o Maven Wrapper:

```cmd
mvnw.cmd spring-boot:run
```

## Linux/macOS

```bash
./mvnw spring-boot:run
```

Ou, caso o Maven esteja instalado:

```bash
mvn spring-boot:run
```

Após iniciar a aplicação, acesse:

```text
http://localhost:8080
```

---

# Gerando o arquivo da aplicação

Para compilar o projeto:

```bash
./mvnw clean package
```

No Windows:

```cmd
mvnw.cmd clean package
```

O artefato será gerado dentro de:

```text
target/
```

Como o projeto está configurado com:

```xml
<packaging>war</packaging>
```

o Maven também pode gerar um arquivo `.war`.

---

# Segurança

O projeto possui alguns mecanismos de segurança implementados:

- Spring Security;
- Controle de acesso por perfil;
- BCrypt para armazenamento das senhas;
- Validação de dados;
- E-mail único para usuários;
- Sessões persistidas no MongoDB;
- Controle de acesso por rota;
- `@PreAuthorize` para proteção de áreas administrativas;
- Invalidação da sessão durante logout.

Exemplo de proteção utilizada no projeto:

```java
@PreAuthorize("hasRole('ADMINISTRADOR')")
```

---

# Principais rotas

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/` | Página inicial | Público |
| GET | `/login` | Tela de login | Público |
| GET | `/cadastro` | Tela de cadastro | Público |
| POST | `/cadastro` | Cadastro de usuário | Público |
| GET | `/dashboard` | Dashboard | Autenticado |
| GET | `/perfil` | Perfil | Autenticado |
| GET | `/chamados` | Página de chamados | Autenticado |
| GET | `/clientes` | Página de clientes | Autenticado |
| GET | `/gerente/painel` | Painel do gerente | Gerente/Admin |
| GET | `/ajustes-horarios` | Ajustes de horários | Gerente/Admin |
| GET | `/administrador/painel` | Painel administrativo | Admin |
| POST | `/administrador/promover/{id}` | Alterar perfil | Admin |
| POST | `/api/chamados` | Criar chamado | Admin |
| GET | `/api/chamados` | Listar chamados | Admin |
| PUT | `/api/chamados/{id}` | Atualizar chamado | Admin |
| POST | `/api/clientes` | Criar cliente | Admin |
| GET | `/api/clientes` | Listar clientes | Admin |

---

# estando a API

As APIs podem ser testadas utilizando ferramentas como:

- Postman;
- Insomnia;
- Thunder Client;
- cURL.

### Exemplo — criar chamado

```http
POST http://localhost:8080/api/chamados
Content-Type: application/json
```

Exemplo de corpo:

```json
{
  "descricao": "Computador não inicia",
  "status": "ABERTO",
  "tipo": "HARDWARE",
  "tecnico": "Técnico 01",
  "usuario": "usuario@exemplo.com"
}
```

### Exemplo — listar chamados

```http
GET http://localhost:8080/api/chamados
```

---

# Interface

A interface utiliza:

- Thymeleaf;
- HTML;
- CSS;
- JavaScript;
- Thymeleaf Layout Dialect.

Os estilos estão organizados em:

```text
src/main/resources/static/css/
```

Incluindo temas:

```text
static/css/temas/padrao.css
static/css/temas/pfc.css
```

O tema atualmente configurado pode ser definido em:

```properties
app.tema=padrao
```

---

# Camadas da aplicação

## Controller

Responsável por receber as requisições HTTP e encaminhá-las para os serviços.

Exemplos:

```text
ChamadoController
ClienteController
ControllerAutenticacao
ControllerUsuario
HomeController
HorarioAtendimentoController
```

## Service

Contém as regras de negócio.

Exemplos:

```text
ChamadoService
ClienteService
HorarioAtendimentoService
ServiceAutenticacao
ServiceUsuario
```

## Repository

Responsável pelo acesso aos dados do MongoDB através do Spring Data.

Exemplos:

```text
ChamadoRepository
ClienteRepository
HorarioAtendimentoRepository
RepositoryUsuario
```

## Entity / Model

Representa os dados persistidos no banco.

```text
Usuario
Chamado
Cliente
HorarioAtendimento
Perfil
```

---

# Modelo de dados simplificado

```text
┌─────────────────────┐
│       Usuario       │
├─────────────────────┤
│ id                  │
│ nome                │
│ email               │
│ senha               │
│ perfil              │
│ ativo               │
│ dataCriacao         │
│ dataAtualizacao     │
└──────────┬──────────┘
           │
           │ usuário
           ▼
┌─────────────────────┐
│       Chamado       │
├─────────────────────┤
│ id                  │
│ descricao           │
│ status              │
│ tipo                │
│ tecnico             │
│ usuario             │
└─────────────────────┘


┌─────────────────────┐
│       Cliente       │
├─────────────────────┤
│ id                  │
│ nome                │
│ telefone            │
│ setor               │
└─────────────────────┘


┌──────────────────────────┐
│   HorarioAtendimento     │
├──────────────────────────┤
│ id                       │
│ setor                    │
│ diaSemana                │
│ horarioInicio            │
│ horarioFim               │
└──────────────────────────┘
```

---

# Fluxo geral do sistema

```text
                    ┌───────────────────┐
                    │     Usuário       │
                    └─────────┬─────────┘
                              │
                              ▼
                    ┌───────────────────┐
                    │      Login        │
                    └─────────┬─────────┘
                              │
                              ▼
                    ┌───────────────────┐
                    │ Spring Security   │
                    └─────────┬─────────┘
                              │
                 ┌────────────┼────────────┐
                 ▼            ▼            ▼
             USUARIO       GERENTE    ADMINISTRADOR
                 │            │            │
                 ▼            ▼            ▼
             Dashboard      Painel       Painel Admin
                 │            │            │
                 └────────────┼────────────┘
                              ▼
                     ┌─────────────────┐
                     │     Services    │
                     └────────┬────────┘
                              ▼
                     ┌─────────────────┐
                     │   Repositories  │
                     └────────┬────────┘
                              ▼
                     ┌─────────────────┐
                     │     MongoDB     │
                     └─────────────────┘
```

---

# Funcionalidades

- [x] Login
- [x] Logout
- [x] Cadastro de usuários
- [x] Validação de cadastro
- [x] Criptografia de senha com BCrypt
- [x] Controle de acesso por perfil
- [x] Dashboard do usuário
- [x] Perfil do usuário
- [x] Painel do gerente
- [x] Painel administrativo
- [x] Alteração de perfil de usuários
- [x] Cadastro de chamados
- [x] Listagem de chamados
- [x] Atualização de chamados
- [x] Cadastro/listagem de clientes via API
- [x] Gerenciamento de horários de atendimento
- [x] Persistência em MongoDB
- [x] Persistência de sessões no MongoDB
- [x] Temas visuais
- [x] Tratamento de páginas de erro

---

# Testes

O projeto pode ser expandido com testes utilizando o ecossistema de testes do Spring.

Uma estrutura recomendada:

```text
src/test/
└── java/
    └── com/
        └── pfc/
            └── thindesk/
                ├── controller/
                ├── service/
                └── repository/
```

---

# Desenvolvimento

**Projeto:** ThinDesk  
**Artifact:** `thindesk`  
**Versão:** `1.0-alpha`  
**Grupo:** `com.pfc`

---