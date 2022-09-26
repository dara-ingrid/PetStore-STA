//1 - Pacote
package petstore;

//2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço da Entidade Pet

    //3.2 - Metodos e Funçoes
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test (priority = 1)//Identifica o método ou função para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then

        given()  // Dado
                .contentType("application/json") //Comum em API REST
                .log().all()
                .body(jsonBody)
        .when()  // Quando
                .post(uri)
        .then()  // Então
                .log().all()
                .statusCode(200)
                .body("name", is ("Banguela"))
                .body("status", is("available"))
                .body("category.name", is ("ANWE9DJI642GQREG37AWS8"))
                .body("tags.name", contains("data"))
        ;
    }

    @Test (priority = 2)
    public void consultarPet () {
        String petID = "1996050345";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petID)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Banguela"))
                .body("category.name", is ("ANWE9DJI642GQREG37AWS8"))
                .body("status", is ("available"))
        .extract()
                .path("category.name")
        ;

        System.out.println("O Token é: " + token);
    }

    @Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("Application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Banguela"))
                .body("status", is ("sold"))
        ;


    }



}
