package tests.ApiTests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.BaseApi;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class PutTaskTest extends BaseApi {

    private static final Logger log = LoggerFactory.getLogger(PostTaskTest.class);
    private final Random random = new Random();

    @BeforeAll
    public static void beforeTest() throws IOException {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }


    @Test
    public void fullUpdateTaskTest(){

        List<TaskModelList.TaskModelWithId> listTasks = given().get(method_get_all_url)
                .then()
                .extract()
                .jsonPath()
                .getList("taskList", TaskModelList.TaskModelWithId.class);


        TaskModelList.TaskModelWithId taskFromList = listTasks.get(random.nextInt(listTasks.size() - 1));

        LocalDateTime dateTime = LocalDateTime.now().plusDays(random.nextInt(30)).truncatedTo(ChronoUnit.SECONDS);

        taskFromList.setDateTime(dateTime);

        TaskModelList.TaskModelWithId updateTask = given().body(taskFromList)
                .contentType(ContentType.JSON)
                .put(method_url)
                .then()
                .statusCode(200)
                .extract().
                as(TaskModelList.TaskModelWithId.class);

        TaskModelList.TaskModelWithId checkTask = given()
                .pathParams("id", updateTask.getId())
                .get(method_url + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(TaskModelList.TaskModelWithId.class);

        Assertions.assertEquals(checkTask.getDateTime().toString(), dateTime.toString());

    }


    @AfterAll
    public static void afterTest(){

    }

}
