[GRAPHQL__BADGE]: https://img.shields.io/badge/GraphQL-e10098?style=for-the-badge&logo=graphql
[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[REACT_BADGE]: https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB
[MYSQL_BADGE]: https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white
[POSTGRESQL_BADGE]: https://img.shields.io/badge/postgresql-%23336791.svg?style=for-the-badge&logo=postgresql&logoColor=white

<h1 align="center" style="font-weight: bold;">Lista de tarefas</h1>

</br>
<div align="center">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring"/>
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java"/>
  <img src="https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB" alt="react"/>
  <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="mysql"/>
  <img src="https://img.shields.io/badge/postgresql-%23336791.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="postgresql"/>
</div>

</br>
<p align="center">
 <a href="#started">Rodando Localmente</a> • 
<a href="#deploy">Deploy</a> • 
 <a href="#routes">API Endpoints</a> •
 <a href="#contribute">Contribute</a>
</p>
</br>

<p align="center">
  <b>Esse projeto foi construído com o intuito de aprender sobre API RESTful, Docker e React. Na lista de tarefas você pode visualizar uma tarefa específica, criar uma nova tarefa selecionando o tipo e a prioridade dela, editar, excluir e marcar ela como concluída.</b>
</p>

<h2 id="started">🚀 Rodando localmente</h2>

<h3>Pré-requisitos</h3>

Ter instalado todas as tecnologias abaixo:

- [NodeJS](https://nodejs.org/en/download/package-manager/current)
- [Git](https://git-scm.com/downloads)
- [SDK 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [MySQL](https://dev.mysql.com/downloads/)

<h3>Clonando</h3>

Abra o git bash em alguma pasta e insira o comando abaixo para clonar o repositório

```bash
git clone https://github.com/friche11/To-do-list.git
```

<h3> Configurações cruciais para o back-end</h2>

Abra o MySQL e crie uma database no mysql executando o comando abaixo
```sql
create database todolist;
```
Mude o nome de usuário e a senha do `application.properties` para seu nome de usuário e sua senha do MySQL. Para isso, vá até as pastas `back-end/src/main/resources`
</br>


<h3>Executando o projeto back-end</h3>

Abra um novo terminal e execute os comandos abaixo para rodar o projeto
```bash
cd back-end
mvn spring-boot:run
```
Se você não tiver o maven instalado, execute
```bash
cd back-end
./mvnw spring-boot:run
```
<h3>Executando o projeto front-end</h3>
Abra um novo terminal e execute os comandos abaixo para rodar o projeto

```bash
cd front-end
npm install 
npm start
```
Você tem os dois projetos rodando cada um em um terminal. Agora você pode utilizar a lista de tarefas localmente, tendo o front-end consumindo os endpoints da API.

<h2 id="deploy">🚀 Deploy</h2>
O deploy foi feito utilizando 2 web services e 1 database de PostgreSQL no https://render.com
</br>
</br>
URL somente da API: https://api-to-do-list-atualizada.onrender.com
</br>
</br>
URL da aplicação utilizando os endpoints da API: https://to-do-list-front-h0fw.onrender.com
</br>
</br>
Como os servidores do render na versão gratuita são suspensos frequentemente, recomendo ver o funcionamento da aplicação pelo vídeo no youtube: https://youtu.be/4cJhcvz5atM


<h2 id="routes">📍 API Endpoints</h2>

A API disponibiliza essas funcionalidades
​
| rota               | descrição                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /api/tasks</kbd>     | lista todas as tarefas da lista [response details](#get-tasks-detail)
| <kbd>GET /api/task/{id}</kbd>     | lista uma tarefa específica da lista [response details](#get-task-detail)
| <kbd>POST /api/create/task</kbd>     | cria uma nova tarefa para a lista [request details](#post-task-detail)
| <kbd>PUT /api/complete/{id}</kbd>     | marca uma tarefa como concluída [request details](#put-completetask-detail)
| <kbd>PUT /api/uncomplete/{id}</kbd>     | marca uma tarefa como não concluída [request details](#put-uncompletetask-detail)
| <kbd>PUT /api/edit/{id}</kbd>     | edita uma tarefa existente da lista [request details](#put-editTask-detail)
| <kbd>DELETE /api/delete/{id}</kbd>     | deleta uma tarefa da lista [request details](#delete-task-detail)

<h3 id="get-tasks-detail">GET  /api/tasks</h3>

**RESPONSE**
```json
[
  {
    "id": 5,
    "description": "Estudar React.js",
    "completed": false,
    "dueDate": "2024-06-29",
    "dueDays": null,
    "type": "DATA",
    "status": "Prevista",
    "priority": "ALTA"
  },
  {
    "id": 3,
    "description": "Apresentar To-do list",
    "completed": true,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Concluída",
    "priority": "ALTA"
  },
  {
    "id": 1,
    "description": "Escrever relatório",
    "completed": false,
    "dueDate": "2024-04-24",
    "dueDays": 3,
    "type": "PRAZO",
    "status": "63 dias de atraso",
    "priority": "ALTA"
  }
]
```

<h3 id="get-task-detail">GET /api/task/3</h3>

**RESPONSE**
```json
{
  "id": 3,
  "description": "Apresentar To-do list",
  "completed": true,
  "dueDate": null,
  "dueDays": 0,
  "type": "LIVRE",
  "status": "Prevista",
  "priority": "ALTA"
}
```

<h3 id="post-task-detail">POST /api/create/task</h3>

**REQUEST**
```json
{
    "description": "Estudar Spring Security",
    "completed": false,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Prevista",
    "priority": "ALTA"
}
```
<h3 id="put-completetask-detail">PUT /api/complete/3</h3>

**REQUEST**
```json
{
    "description": "Apresentar To-do list",
    "completed": true,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Concluída",
    "priority": "ALTA"
}
```
<h3 id="put-uncompletetask-detail">PUT /api/uncomplete/3</h3>

**REQUEST**
```json
{
    "description": "Apresentar To-do list",
    "completed": false,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Prevista",
    "priority": "ALTA"
}
```
<h3 id="put-editTask-detail">PUT /api/edit/3</h3>

**REQUEST**
```json
{
    "description": "Apresentar lista de tarefas",
    "completed": false,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Prevista",
    "priority": "ALTA"
}
```
<h3 id="delete-task-detail">DELETE /api/delete/9</h3>

**REQUEST**
```json
{
    "description": "Estudar Spring Security",
    "completed": false,
    "dueDate": null,
    "dueDays": 0,
    "type": "LIVRE",
    "status": "Prevista",
    "priority": "ALTA"
}
```



<h2 id="contribute">📫 Contribuindo</h2>

Se você deseja contribuir, clone esse repositório, crie sua branch e coloque a mão na massa!

1. `git clone https://github.com/friche11/To-do-list.git`
2. `git checkout -b feature/NAME`
3. No final, abra um Pull Request explicando o problema/melhoria identificado, o que foi feito para resolver e screenshots das alterações visuais :)


[📝 Como fazer um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrões de Commit](https://github.com/iuricode/padroes-de-commits)
