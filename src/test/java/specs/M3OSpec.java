package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static configs.ApiConfig.getApiToken;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class M3OSpec {
    public static RequestSpecification baseRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON)
            .header("authorization", getApiToken());

    public static ResponseSpecification okResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();


}
