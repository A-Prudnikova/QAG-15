import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegresInTests {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void userNotFound() {
        given()
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void createUser() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void updateUserInfo() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void loginWithNoPassword() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"peter@klaven\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
