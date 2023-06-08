package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import models.*;

import static io.restassured.RestAssured.given;
import static java.lang.Thread.sleep;
import static specs.M3OSpec.baseRequestSpec;
import static specs.M3OSpec.okResponseSpec;

public class ApiSteps {

    @Step("Создаём в таблице {0} пользователя {1}")
    public static CreateUserResponseModel createUserRecord(String table, UserRecord userRecord) {
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setTable(table);
        createUserModel.setRecord(userRecord);

        ValidatableResponse response =
                given(baseRequestSpec)
                        .body(createUserModel)
                        .when()
                        .post("/v1/db/Create")
                        .then()
                        .spec(okResponseSpec);

        CreateUserResponseModel result = response.extract().as(CreateUserResponseModel.class);

        return result;
    }


    @Step("Читаем из таблицы пользователя по запросу {0}")
    public static UserRecords readRecordFromTableByID(ReadByIDModel readByIDModel) {

        UserRecords results =
                given(baseRequestSpec)
                        .body(readByIDModel)
                        .when()
                        .post("/v1/db/Read")
                        .then()
                        .spec(okResponseSpec)
                        .extract().as(UserRecords.class);

        return results;
    }


    @Step("Стираем таблицу {0}")
    public static void dropTable(String tableName) {
        TableModel tableModel = new TableModel();
        tableModel.setTable(tableName);

        given(baseRequestSpec)
                        .body(tableModel)
                        .when()
                        .post("/v1/db/DropTable")
                        .then();
    }

    @Step("Стираем в базе таблицы, которые использовались в тестах: {0}")
    @SneakyThrows
    public static void cleanData(String[] tables) {
        for (String str : tables) {
            dropTable(str);

            // Таймаут чтобы избежать превышение квоты запросов в секунду на бесплатном плане сервиса
            sleep(1000);
        }
    }

}
