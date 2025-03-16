package tests;

import lombok.Data;
import org.example.MyApplication;
import org.example.config.AppConfig;
import org.example.util.LoadYaml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.IOException;



public class BaseApi{

    protected AppConfig.AppProperties appProperties;

    public static AppConfig getAppConfig() throws IOException{
        return LoadYaml.loadConfig("src/test/resources/application.yml");
    }
}
