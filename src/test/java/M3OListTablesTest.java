import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import models.*;
import org.junit.jupiter.api.*;

import static configs.ApiConfig.getApiToken;
import static configs.ApiConfig.runApiConfig;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.M3OSpec.*;
import static steps.ApiSteps.*;


public class M3OListTablesTest {

    static String[] testTables = new String[]{"example1"};

    @BeforeEach
    @SneakyThrows
    public void beforeAll() {
        runApiConfig();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Проверяем получения списка таблиц с таблицей persistent")
    @Description("Тестируем получение списка таблиц с таблицей persistent, которая не удаляется")
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
    @Tag("regression")
    @DisplayName("Проверяем, что запрос создания без нужного джейсона приводит к ошибке 400")
    @Description("Пытаемся создать пользователя, не отправляя джейсона на эндпойнт -- ошибка  400")
    void tryToCreateUserWithoutRecordTest() {

        ErrorModel errorModel = step("Trying to create a user with empty json", () ->
                given(baseRequestSpec)
                        .when()
                        .post("/v1/db/Create")
                        .then()
                        .log().status()
                        .spec(errorCode400ResponseSpec)
                        .extract().as(ErrorModel.class));
        step("ID ошибки это 'db.create'", () -> assertThat(errorModel.getId()).isEqualTo("db.create"));
        step("detail ошибки это 'missing record'", () -> assertThat(errorModel.getDetail()).isEqualTo("missing record"));
        step("status ошибки это 'Bad Request'", () -> assertThat(errorModel.getStatus()).isEqualTo("Bad Request"));

    }

    @Test
    @Tag("smoke")
    @DisplayName("Проверяем создание пользователя")
    @Description("Создание пользователя, проверка респонса создания,  чтение-проверка записи о созданном пользователе")
    void createUserTestWithModel() {

        CreateUserModel createUserModel = new CreateUserModel();
        UserRecord userRecord = new UserRecord(43, "1", true, "Jane");

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
    @DisplayName("Проверяем обновление записи о пользователе в таблице")
    @Description("Обновление ID пользователя, чтение-проверка записи о созданном пользователе")
    void updateIdUserTest() {
        CreateUserModel createUserModel = new CreateUserModel();
        UserRecord userRecord = new UserRecord(43, "1", true, "Jane");

        createUserModel.setTable("example");
        createUserModel.setRecord(userRecord);

        ValidatableResponse response = step("Update user", () ->
                given(baseRequestSpec)
                        .body(createUserModel)
                        .when()
                        .post("/v1/db/Update")
                        .then()
                        .spec(okResponseSpec));

        System.out.println(response.extract().asString());


    }






}

