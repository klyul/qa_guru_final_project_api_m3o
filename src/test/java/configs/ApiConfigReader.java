package configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.FIRST)
@Config.Sources({
        "classpath:configs/${env}.properties",
        "classpath:configs/default.properties",
        "system:properties"
})
public interface ApiConfigReader extends Config {

    @Key("baseUrl")
    @DefaultValue("https://api.m3o.com")
    String getBaseUrl();

    @Key("apiKey")
    @DefaultValue("Bearer ZDQ3YWQ0NDQtNTJiNC00NzIyLTk5ODYtMDhmYTIwZDFmNWE3")
    String getApiKey();

}