package helpers;

import io.qameta.allure.Step;
import models.CreateUserModel;
import models.TableModel;
import models.UserRecord;

import static io.restassured.RestAssured.given;
import static specs.M3OSpec.baseRequestSpec;
import static specs.M3OSpec.okResponseSpec;

public class UserHelperMethods {

    @Step("Создаём в таблице {0} пользователя {1}")
    public static void createUserRecord (String table, UserRecord userRecord){
        CreateUserModel createUserModel = new CreateUserModel();

        createUserModel.setTable(table);
        createUserModel.setRecord(userRecord);

        given(baseRequestSpec)
                .body(createUserModel)
                .when()
                .post("/v1/db/Create")
                .then()
                .spec(okResponseSpec);
    }



    public static void dropTable(String tableName){
        TableModel tableModel = new TableModel();
        tableModel.setTable(tableName);

        given(baseRequestSpec)
                .body(tableModel)
                .when()
                .post("/v1/db/Create")
                .then()
                .spec(okResponseSpec);
    }

    @Step("Вызываем очистку данных")
    public static void cleanData(){
        dropTable("examples");
    }

}