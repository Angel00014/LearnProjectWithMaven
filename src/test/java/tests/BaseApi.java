package tests;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.example.config.AppConfig;
import org.example.model.TaskModelList;
import org.example.util.LoadYaml;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class BaseApi {

    protected AppConfig.AppProperties appProperties;

    @Getter
    private String base_url;
    protected String method_url;
    protected String method_get_all_url;
    protected static HashMap<String, Object> context = new HashMap<String, Object>();

    public BaseApi(){
        try {
            this.base_url = getAppConfig().getApp().getBaseUrl();
            this.method_url = getBase_url()  + "/api/task";
            this.method_get_all_url = getBase_url() + "/api/tasks";
        }catch (IOException e){
            System.out.println(e);
        }

    }

    public static AppConfig getAppConfig() throws IOException{
        return LoadYaml.loadConfig("src/test/resources/application.yml");
    }

    protected TaskModelList.TaskModel randomTask() {
        Faker faker = new Faker();
        Random random = new Random();
        int randomDays = random.nextInt(30);
        List<String> listTimeBefore = new ArrayList<String>() {{
            add("5m");
            add("10m");
            add("15m");
            add("30m");
            add("45m");
        }};
        TaskModelList.TaskModel randomTask = new TaskModelList.TaskModel();

        randomTask.setName(faker.name().fullName());
        randomTask.setTimeBefore(listTimeBefore.get(random.nextInt(listTimeBefore.size() - 1)));
        randomTask.setDateTime(LocalDateTime.now().plusDays(randomDays));

        return randomTask;
    }
}
