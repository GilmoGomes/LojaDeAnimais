package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Pets {
    public String tokenGeral; //variavel para receber o token

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Create / Incluir / POST
    @Test
    public void incluirPet() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                                       // Dado que
                .contentType("application/json")     // Tipo de conteúdo da requisição
                // "text/xml" para web services comuns
                // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                                 // Quando
                .post("https://petstore.swagger.io/v2/pet") // Operação e endpoint
        .then()                                 // Então
                .log().all()                        // Gerar um log completo da resposta
                .statusCode(200)                    // Validou o código de status da requisição como 200
                // .body("code", is(200))  // Valida o code como 200
                .body("id", is(4210))    // Validou a tag id com o conteúdo esperado
                .body("name", is("Toto")) // Validou a tag nome como Dog
                .body("tags.name", contains("adocao")) //validou a tag name filha da tag Tags
        ;
        System.out.print("Executou o serviço");
    }

    // Reach or Research  /Consultar / Get
    @Test
    public void consultarPet() {
        String petId = "4210";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .get("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()                                             // Então
                .log().all()                                            // Mostrar tudo que foi recebido
                .statusCode(200)                                        // Validou que a operação foi realizada
                .body("name", is("Toto"))                // Validou o nome do pet
                .body("category.name", is("dog"))            // Validou a espécie
        ;
    }

    @Test
    public void ordemDaExecucao() throws IOException {
        incluirPet();
        consultarPet();
        alterarPet();
        excluirPet();
    }

    // Update  /Alterar  /Put
    @Test
    public void alterarPet() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/petput.json");

        given()                                 // Dado que
                .contentType("application/json")    // Tipo de conteúdo da requisição
                // "text/xml" para web services comuns
                // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                             // Quando
                .put("https://petstore.swagger.io/v2/pet")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Toto"))
                .body("status", is("adotado"))
        ;

    }

    // Delete / Excluir / Delete
    @Test
    public void excluirPet() {
        String petId = "4210";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .delete("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()
                .log().all()
                .statusCode(200)

        ;

    }

    // Login
    @Test
    public void loginUser() {
        // public String loginUser(){

        String token =
                given()                                             // Dado que
                        .contentType("application/json")                        // Tipo de conteúdo da requisição
                        .log().all()                                            // Mostrar tudo que foi enviado
                .when()
                        .get("https://petstore.swagger.io/v2/user/login?username=charlie&password=brown")
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("message", containsString("logged in user session:"))
                        .extract()
                        .path("message");
        tokenGeral = token.substring(24); // separa o token da frase
        System.out.println("o tokens valido eh " + tokenGeral);

        //return tokengeral
    }



    // Create / Incluir / POST
    @Test
    public void incluirUser() throws IOException {
        // Ler o conteúdo do arquivo petuser.json
        String jsonBody = lerJson("data/petputuser.json");

        given()                                       // Dado que
                .contentType("application/json")     // Tipo de conteúdo da requisição
                // "text/xml" para web services comuns
                // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                                 // Quando
                //.post("https://petstore.swagger.io/v2/user/createWithList") // Operação e endpoi
                .post("https://petstore.swagger.io/v2/user/") // Operação e endpoi
        .then()                                 // Então
                .log().all()                        // Gerar um log completo da resposta
                .statusCode(200)                // Validou o código de status da requisição como 200
                .body("code", is(4210))  // Valida o code como 200
                .body("id", is(4210))    // Validou a tag id com o conteúdo esperado
                .body("username", is("Gil"))    // Validou a tag nome com o conteúdo esperado
                .body("firstname", is("Gilmo"))    // Validou a tag  o primeiro nome com o conteúdo esperado
                .body("lastname", is("Gomes"))    // Validou a tag  o sobre nome com o conteúdo esperado

        ;

        System.out.println("Executou o servico");
    }

    // Reach or Research  /Consultar / Get
    @Test
    public void consultarUser() {
        String petId = "4210";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .get("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()                                             // Então
                .log().all()                                            // Mostrar tudo que foi recebido
                .statusCode(4210)                                        // Validou que a operação foi realizada
                .body("username", is("Gil"))                // Validou o nome do pet

        ;
    }

    @Test
    public void ordemDaExecucaoUser() throws IOException {
        incluirUser();
        consultarUser();
        alterarUser();
        excluirUser();
    }

    // Update  /Alterar  /Put
    @Test
    public void alterarUser() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/petput.json");

        given()                                 // Dado que
                .contentType("application/json")    // Tipo de conteúdo da requisição
                // "text/xml" para web services comuns
                // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
        .when()                             // Quando
                .put("https://petstore.swagger.io/v2/pet")
        .then()
                .log().all()
                .statusCode(200)
                .body("username", is("Gilmo"))

        ;

    }

    // Delete / Excluir / Delete
    @Test
    public void excluirUser() {
        String petId = "4210";

        given()                                             // Dado que
                .contentType("application/json")                        // Tipo de conteúdo da requisição
                .log().all()                                            // Mostrar tudo que foi enviado
        .when()                                             // Quando
                .delete("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()
                .log().all()
                .statusCode(200)

        ;

    }



}