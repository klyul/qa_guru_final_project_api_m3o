import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import models.CreateUserModel;
import models.ErrorModel;
import models.TablesModel;
import models.UserRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static configs.ApiConfig.getApiToken;
import static configs.ApiConfig.runApiConfig;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.M3OSpec.*;

public class M3OListTablesTest {

    @BeforeEach
    void beforeAll() {
        runApiConfig();
    }

    @Test
    @Tag("smoke")
    void getTables() {

        TablesModel tablesModel = step("Listing tables", () ->
                given(baseRequestSpec)
                        .when()
                        .get("/v1/db/ListTables")
                        .then()
                        .spec(okResponseSpec)
                        .extract().as(TablesModel.class));

        step("Table with name 'persistent' exists", () ->
                assertThat(tablesModel.getTables()).contains("persistent"));

    }

    @Test
    @Tag("smoke")
    @Description("Request sent with missing user record returns error 400")
    void tryToCreateUserWithoutRecordTest() {

        ErrorModel errorModel = step("Trying to create a user with empty json", () ->
                given(baseRequestSpec)
                        .when()
                        .post("/v1/db/Create")
                        .then()
                        .log().status()
                        .spec(errorCode400ResponseSpec)
                        .extract().as(ErrorModel.class));
        step("Error id is 'db.create'", () -> assertThat(errorModel.getId()).isEqualTo("db.create"));
        step("Error detail is 'missing record'", () -> assertThat(errorModel.getDetail()).isEqualTo("missing record"));
        step("Error status is 'Bad Request'", () -> assertThat(errorModel.getStatus()).isEqualTo("Bad Request"));

    }

    @Test
    @Tag("smoke")
    void createUserTestWithModel() {

        CreateUserModel createUserModel = new CreateUserModel();
        UserRecord userRecord = new UserRecord(42, "1", true, "Jane");

        createUserModel.setTable("example");
        createUserModel.setRecord(userRecord);

        ValidatableResponse response = step("Create user", () ->
                given(baseRequestSpec)
                        .body(createUserModel)
                        .when()
                        .post("/v1/db/Create")
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
                        .spec(okResponseSpec));

        System.out.println(response.statusCode(200));

    }

    @Test
    void demoNoSpecificationsExample() {

        ValidatableResponse response = step("Listing tables", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .contentType(JSON)
                        .header("authorization", getApiToken())
                        .when()
                        .get("/v1/db/ListTables")
                        .then()
                        .log()
                        .all()
                        .statusCode(200)
                        .and());

    }





}

