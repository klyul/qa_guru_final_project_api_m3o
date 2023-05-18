import configs.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static configs.ApiConfig.runApiConfig;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.M3OSpec.baseRequestSpec;
import static specs.M3OSpec.okResponseSpec;

public class M3OListTablesTest {

    @BeforeEach
    void beforeAll() {
        runApiConfig();
    }

    @Test
    @Tag("smoke")
    void getTables() {

        ValidatableResponse response = step("Listing tables", () ->
                given(baseRequestSpec)
                        .when()
                        .get("/v1/db/ListTables")
                        .then()
                        .spec(okResponseSpec));

        System.out.println(response.extract().asString());

    }
    @Test
    @Tag("smoke")
    void createUserTest() {

        ValidatableResponse response = (ValidatableResponse) step("Create user", () ->
                given(baseRequestSpec)
                        .when()
                        .post("/v1/db/Create")
                        .then()
                        .log().status()
                        .log().body()
                        .spec(okResponseSpec))
                .statusCode(200));
        System.out.println(response.extract().asString());

    }
}

