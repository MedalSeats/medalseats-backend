# Medal Seats - Backend
Projeto de Engenharia de Software Unicamp (Backend)
Objetivo do projeto: Criar um website que faça a compra de ingressos para os Jogos Olímpicos de Paris de 2024, com o objetivo principal de oferecer uma experiência fluida e adaptada às preferências individuais dos usuários.

### Integrantes do grupo:    
    Gabriel Freitas Pinheiro - 222339
    Gustavo Ferreira Gitzel - 223559
    Maria Eduarda Elias Rocha - 248408
    Pedro Sanchez Bitencourt - 231133
    Sara Beatriz da Silva Oliveira - 231288

## Pré-requisitos

Para executar este projeto, você precisará ter instalado em sua máquina:

- Docker
- Docker Compose
- Java 17

## Configuração

O deployment `medalseats-management` utiliza as portas `8080` e `8081`. Certifique-se de que estas portas estão disponíveis em sua máquina.

## Executando o Projeto

Siga os passos abaixo para executar o projeto:

1. *Docker Compose Down*

   Para garantir que não haja conflitos com serviços anteriormente executados, execute o seguinte comando para remover serviços órfãos:

   ```bash
   docker-compose down --remove-orphans
   ```

2. *Docker Compose Up*

   Para iniciar todos os serviços definidos no arquivo docker-compose.yml, execute:

    ```bash
   docker-compose up
   ```
   
    Caso precise de permissão, execute:

    ```bash
   sudo docker-compose up
   ```

    Este comando irá baixar as imagens necessárias e iniciar os containers.

3. *Flyway Clean e Migrate*

   Antes de iniciar a aplicação, é necessário preparar o banco de dados. Utilize o Gradle para executar as tarefas flywayClean e flywayMigrate:

    ```bash
    ./gradlew flywayClean flywayMigrate
    ```
   
    Isso irá limpar o banco de dados e aplicar as migrações necessárias.

4. *Executar a Aplicação Spring Boot*

   Com o banco de dados preparado, você pode iniciar a aplicação Spring Boot executando:
    
   ```bash
    ./gradlew medalseats-deployments:medalseats-management:bootRun
    ```
   
    Isso irá iniciar a aplicação na porta 8080 (ou 8081, conforme configurado).
   
   ***Para esclarecer:***
   *98% EXECUTING* Indica que a aplicação está em execução. A tarefa de execução está quase completa, mas não pode atingir 100% até que você pare a aplicação. A aplicação Spring Boot está funcionando normalmente. Você pode acessar os endpoints, verificar logs, etc.

## Acessando a Aplicação
Após iniciar a aplicação, você pode acessá-la através do navegador no endereço http://localhost:8080 ou http://localhost:8081, dependendo da configuração de portas utilizada.

   