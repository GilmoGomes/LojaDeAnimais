package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    @Test
    public void ordenarExecucao() throws IOException {
        venderPet();
        consVendaPet();
        excluirVendapet();
    }


    @Test
    public void venderPet() throws IOException {
        String jsonBody = lerJson("data/order.json");

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/store/order")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(6411))
                .body("status", is("placed"))
                .body("complete", is(true))
        ;
    }

    @Test
    public void consVendaPet() throws IOException {
        String jsonBody = lerJson("data/order.json");

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/store/inventory")
        .then()
                .log().all()
                .statusCode(200)
               // .body("petId", is(0))


        ;

    }
    @Test
    public void excluirVendapet(){
        int petId = 4201;
        given()                                             // Dado que
                    .contentType("application/json")                        // Tipo de conteúdo da requisição
                    .log().all()                                            // Mostrar tudo que foi enviado
            .when()                                             // Quando
                    .delete("https://petstore.swagger.io/v2/store/order/6411")
            .then()
                .log().all()
                .statusCode(200)

        ;

    }


}
