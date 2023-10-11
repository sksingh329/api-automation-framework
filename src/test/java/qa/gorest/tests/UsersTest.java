package qa.gorest.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UsersTest {
    @Test
    public void listAllUsersTest(){
        RestAssured.baseURI = "https://gorest.co.in";

        Response listUserResponse = RestAssured.given()
                .when()
                .get("/public/v2/users");

        //Fetching response headers
        System.out.println(listUserResponse.getHeader("Content-Type"));
        System.out.println(listUserResponse.getHeader("x-pagination-page"));
        System.out.println(listUserResponse.getHeader("x-pagination-limit"));
        System.out.println(listUserResponse.getHeader("x-pagination-total"));
        System.out.println(listUserResponse.getHeader("x-pagination-pages"));

        //Parsing response body
        JsonPath responseBodyJsonPath = listUserResponse.jsonPath();
        System.out.println(responseBodyJsonPath.getString("[0].id"));
        System.out.println(responseBodyJsonPath.getString("[0].name"));
        System.out.println(responseBodyJsonPath.getString("[0].email"));
        System.out.println(responseBodyJsonPath.getString("[0].gender"));
        System.out.println(responseBodyJsonPath.getString("[0].status"));
    }
}
