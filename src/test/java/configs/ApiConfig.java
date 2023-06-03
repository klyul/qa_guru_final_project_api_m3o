package configs;

import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;

public class ApiConfig {

    private static String apiToken;

    public ApiConfig() {
        runApiConfig();
    }

    public static String getApiToken() {
        return apiToken;
    }

    public static void runApiConfig() {

        ApiConfigReader apiConfigReader = ConfigFactory.create(ApiConfigReader.class);

        RestAssured.baseURI = apiConfigReader.getBaseUrl();
        apiToken = apiConfigReader.getApiKey();

    }

}
