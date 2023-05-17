import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class M3OListTablesTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.m3o.com";
    }

    @Test
    @Tag("simple_sampler")
    @Tag("simple_api")
    void getTables() {

        ValidatableResponse response =
                given().contentType(ContentType.JSON)
                        .header("authorization", "Bearer ZDQ3YWQ0NDQtNTJiNC00NzIyLTk5ODYtMDhmYTIwZDFmNWE3")
                        .log().all()
                        .when()
                        .get("/v1/db/ListTables")
                        .then()
                        .log().all();

    }
}