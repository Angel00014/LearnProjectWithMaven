package tests.ApiTests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.BaseApi;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class PostTaskTest extends BaseApi {

    private static final Logger log = LoggerFactory.getLogger(PostTaskTest.class);
    private static String method_url;
    private final Random random = new Random();

    @BeforeAll
    public static void beforeTest() throws IOException {
        method_url = getAppConfig().getApp().getBaseUrl() + "/api/task";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }


    @Test
    public void createTaskTest(){

        TaskModelList.TaskModelWithId createTask = given().body(randomTask())
                .contentType(ContentType.JSON)
                .post(method_url)
                .then()
                .statusCode(201)
                .extract().
                as(TaskModelList.TaskModelWithId.class);

        given().pathParams("id", createTask.getId())
                .get(method_url + "/{id}")
                .then()
                .statusCode(200);
    }


    @AfterAll
    public static void afterTest(){

    }


}
