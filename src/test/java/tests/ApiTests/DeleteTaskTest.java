package tests.ApiTests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.BaseApi;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class DeleteTaskTest extends BaseApi {

    private static final Logger log = LoggerFactory.getLogger(PostTaskTest.class);
    private final Random random = new Random();

    @BeforeAll
    public static void beforeTest(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter() , new AllureRestAssured());
    }

    @Test
    public void deleteTaskTest(){

        List<TaskModelList.TaskModelWithId> listTasks = given().get(method_get_all_url)
                .then()
                .extract()
                .jsonPath()
                .getList("taskList", TaskModelList.TaskModelWithId.class);


        TaskModelList.TaskModelWithId taskFromList = listTasks.get(random.nextInt(listTasks.size() - 1));

        given().pathParams("id", taskFromList.getId())
                .delete(method_url+ "/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParams("id", taskFromList.getId())
                .get(method_url + "/{id}")
                .then()
                .statusCode(404);

    }


    @AfterAll
    public static void afterTest(){

    }

}
