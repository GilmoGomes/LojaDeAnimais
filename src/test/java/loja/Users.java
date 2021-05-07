package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Users {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordenarExecucao() throws IOException {
        incluirUsuario();
        consultarUsuario();
        alterarUsuario();
        excluirUsuario();
    }

    @Test
    public void incluirUsuario() throws IOException {
        String jsonBody = lerJson("data/usersteste.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/user")
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5311"))

        ;
        System.out.print("Executou o serviço");
    }

    @Test
    public void consultarUsuario(){
        String username = "Ggomes";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(5311))
                .body("firstName", is("Gilmo"))
                .body("lastName", is("Gomes"))
                .body("email", is("gilmo.gomes@teste.com"))
        ;

    }

    @Test
    public void alterarUsuario() throws IOException {
        String jsonBody = lerJson("data/userstesteput.json");
        String username = "Ggomes";

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .put("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5311"))
        ;

    }
    @Test
    public void excluirUsuario(){
        String username = "Ggomes";

        given()
                .log().all()
                .contentType("application/json")
        .when()
                .delete("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is(username))
        ;

    }


}
