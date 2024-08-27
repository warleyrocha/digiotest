# digiotest
Test for Backend Developer Digio

Aplicação feita com Spring Boot, Spring Data com Banco H2, Rest.

<h1>Testes Utilizando o Postman</h1>

Nos arquivos está disponivel um json com os arquivos. Deve ser criada uma variavel de ambiente com o nome de host

Para local:

    http://localhost:8080

<h1>Subindo Aplicação Desenvolvimento</h1>

rodar o comando

```
mvn spring-boot:run
```

<h1>Testes com Mokito</h1>

Para rodar a classe de testes configurada com as conexão de banco mokadas (Mokito)

Deve-se executar o seguinte comando:

```
mvn clean test 
```

Com isso os testes dos serviços irão rodar sem necessidade de conexão com o banco.
