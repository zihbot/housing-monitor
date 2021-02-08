package ml.zihbot.housing_monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties()
public class DatabaseConfig {
    /*private String url;
    private String user;
    private String password;*/
    @Value("${test}")
    private String test;

    /*public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }*/

    public String getPassword() {
        return test;
    }
}
