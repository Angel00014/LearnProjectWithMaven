package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
public class AppConfig {

    @JsonProperty("app")
    private AppProperties app;

    @Getter
    public static class AppProperties {
        @JsonProperty("base_url")
        private String baseUrl;
    }


}
