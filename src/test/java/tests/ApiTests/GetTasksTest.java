package tests.ApiTests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.model.ResponseModel;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseApi;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class  GetTasksTest extends BaseApi {

    private final Random random = new Random();

    @BeforeAll
    public static void beforeTest() throws IOException {
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void getTasksSuccess() throws IOException {
        given().get(method_get_all_url)
                .then()
                .statusCode(200)
                .extract()
                .as(TaskModelList.class);;

    }

    @ParameterizedTest
    @ValueSource(ints = {1,100, 200})
    public void getTasksWithParam(int limit) throws IOException {
        given().queryParams("limit", limit)
                .get(method_get_all_url)
                .then()
                .statusCode(200)
                .body("taskList", hasSize(lessThanOrEqualTo(limit)));

    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 201, 0})
    public void getTasksParamOverString(int limit){
        given().queryParams("limit", limit)
                .get(method_get_all_url)
                .then()
                .statusCode(400)
                .extract()
                .as(ResponseModel.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"string", "+", "null"})
    public void getTasksParamOverString(String limit){
        given().queryParams("limit", limit)
                .get(method_get_all_url)
                .then()
                .statusCode(400);
    }

    @Test
    public void getIdFromResponse(){

        List<TaskModelList.TaskModelWithId> listTasks = given()
                .get(method_get_all_url)
                .then()
                .extract()
                .jsonPath()
                .getList("taskList", TaskModelList.TaskModelWithId.class);


        TaskModelList.TaskModelWithId taskFromList = listTasks.get(random.nextInt(listTasks.size() - 1));

        TaskModelList.TaskModelWithId checkTask = given()
                .pathParams("id", taskFromList.getId())
                .get(method_url + "/{id}")
                .then()
                .extract()
                .as(TaskModelList.TaskModelWithId.class);

        Assertions.assertEquals(taskFromList, checkTask, "Сравнение полученных данных");

    }


    @AfterAll
    public static void afterTest(){

    }

}
