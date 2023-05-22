import io.qameta.allure.Description;
import io.qameta.allure.Step;
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

    static String[] testTables = new String[]{"example1","example2"};
    @BeforeAll
    @SneakyThrows
    static void beforeAll() {
        runApiConfig();
        cleanData(testTables);
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

        UserRecord userRecord = new UserRecord(42, "1", true, "Jane");

        CreateUserResponseModel response = createUserRecord(testTables[0], userRecord);

        step("Проверяем что ID в респонсе соответствует заданному", ()-> assertThat(response.getId()).isEqualTo("1"));

        UserRecords records = readRecordFromTableByID(new ReadByIDModel("1", testTables[0]));
        UserRecord returnedRecord = records.getRecords().get(0);

        step("У прочтенной записи age = 42", ()-> assertThat(returnedRecord.getAge()).isEqualTo(42));
        step("У прочтенной записи id = '1'", ()-> assertThat(returnedRecord.getId()).isEqualTo("1"));
        step("У прочтенной записи isActive = true", ()-> assertThat(returnedRecord.isActive()).isEqualTo(true));
        step("У прочтенной записи name = 'Jane'", ()-> assertThat(returnedRecord.getName()).isEqualTo("Jane"));

    }


    @Test
    @Tag("smoke")
    @DisplayName("Проверяем обновление записи о пользователе в таблице")
    @Description("Обновление ID пользователя, чтение-проверка записи о созданном пользователе")
    void updateIdUserTest() {
        UserRecord userRecord = new UserRecord(42, "2", true, "Jane");
        CreateUserResponseModel response = createUserRecord(testTables[1], userRecord);
        UserRecord userRecord2 = new UserRecord(21, "2", false, "Kris");
        CreateUserModel createUserModel2 = new CreateUserModel(userRecord2, testTables[1]);

        ValidatableResponse response2 = step("Update user", () ->
                given(baseRequestSpec)
                        .body(createUserModel2)
                        .when()
                        .post("/v1/db/Update")
                        .then()
                        .spec(okResponseSpec));

        UserRecords records = readRecordFromTableByID(new ReadByIDModel("2", testTables[1]));
        UserRecord returnedRecord = records.getRecords().get(0);

        step("У прочтенной записи age = 21", ()-> assertThat(returnedRecord.getAge()).isEqualTo(21));
        step("У прочтенной записи id = '2'", ()-> assertThat(returnedRecord.getId()).isEqualTo("2"));
        step("У прочтенной записи isActive = false", ()-> assertThat(returnedRecord.isActive()).isEqualTo(false));
        step("У прочтенной записи name = 'Kris'", ()-> assertThat(returnedRecord.getName()).isEqualTo("Kris"));


    }

    //ReadByIDModel
    @Test
    @Tag("smoke")
    @DisplayName("Проверяем удаление записи о пользователе в таблице")
    @Description("Проверка удаления созданной записи о пользователе")
    void deleteUserTest() {
        UserRecord userRecord = new UserRecord(42, "3", true, "Jane");
        CreateUserResponseModel response = createUserRecord(testTables[1], userRecord);
        ReadByIDModel readByIDModel = new ReadByIDModel("3",testTables[1]);

        ValidatableResponse response2 = step("Delete user", () ->
                given(baseRequestSpec)
                        .body(readByIDModel)
                        .when()
                        .post("/v1/db/Delete")
                        .then()
                        .spec(okResponseSpec));

        UserRecords records = readRecordFromTableByID(new ReadByIDModel("3", testTables[1]));

        step("У прочтенной записи age = 21", ()-> assertThat(records.getRecords().size()).isEqualTo(0));

    }


        @AfterAll
    static void afterAll() {
        //TODO: enable cleaning when reading test be working
     //   cleanData(testTables);
    }


}

