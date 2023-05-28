package configs;

import io.restassured.RestAssured;

public class ApiConfig {

    private static String apiToken;

    public ApiConfig() {
        runApiConfig();
    }

    public static String getApiToken() {
        return apiToken;
    }

    public static void runApiConfig() {

        apiToken = "Bearer ZDQ3YWQ0NDQtNTJiNC00NzIyLTk5ODYtMDhmYTIwZDFmNWE3";
        RestAssured.baseURI = "https://api.m3o.com";

    }

}
