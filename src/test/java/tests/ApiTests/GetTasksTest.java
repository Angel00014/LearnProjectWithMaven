package tests.ApiTests;

import io.restassured.response.Response;
import org.example.config.AppConfig;
import org.example.model.ResponseModel;
import org.example.model.TaskModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.BaseApi;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;

public class  GetTasksTest extends BaseApi {

    private static final Logger log = LoggerFactory.getLogger(GetTasksTest.class);
    private static String method_url;

    @BeforeAll
    public static void beforeTest() throws IOException {
        method_url = getAppConfig().getApp().getBaseUrl() + "/api/tasks";
    }

    @Test
    public void getTasksSuccess() throws IOException {
        given().get(method_url)
                .then()
                .log()
                .all()
                .statusCode(200);

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 200})
    public void getTasksWithParam(int limit) throws IOException {
        given().queryParams("limit", limit)
                .get(method_url)
                .then()
                .log()
                .all()
                .statusCode(200);

    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 201, 0})
    public void getTasksParamOverString(int limit){
        given().queryParams("limit", limit)
                .get(method_url)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(ResponseModel.class);


    }

    @ParameterizedTest
    @ValueSource(strings = {"string", "+", "null"})
    public void getTasksParamOverString(String limit){
        given().queryParams("limit", limit)
                .get(method_url)
                .then()
                .log()
                .all()
                .statusCode(400);
    }

    @Test
    public void checkResponseModel(){
        given().get(method_url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(TaskModelList.class);
    }


    @AfterAll
    public static void afterTest(){

    }

}
