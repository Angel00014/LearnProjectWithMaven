package tests.ApiTests;

import io.restassured.http.ContentType;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import tests.BaseApi;

import java.io.IOException;
import java.util.Random;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PostTaskTest extends BaseApi {

    private static final Logger log = LoggerFactory.getLogger(PostTaskTest.class);
    private static String method_url;
    private final Random random = new Random();

    @BeforeAll
    public static void beforeTest() throws IOException {
        method_url = getAppConfig().getApp().getBaseUrl() + "/api/task";
    }


    @Test
    public void createTaskTest(){
        LocalDateTime dateTime = LocalDateTime.of(2025, 3, 17, 14, 20, 47);
        System.out.println(dateTime);
        TaskModelList.TaskModel taskModel = TaskModelList.TaskModel.builder()
                .name("Тест")
                .dateTime(dateTime)
                .timeBefore("15m")
                .build();

        TaskModelList.TaskModelWithId createTask = given().body(taskModel)
                .contentType(ContentType.JSON)
                .post(method_url)
                .then()
                .log()
                .all()
                .statusCode(201)
                .extract().
                as(TaskModelList.TaskModelWithId.class);

        given().pathParams("id", createTask.getId())
                .get(method_url + "/{id}")
                .then()
                .log()
                .all()
                .statusCode(200);
    }


    @AfterAll
    public static void afterTest(){

    }


}
