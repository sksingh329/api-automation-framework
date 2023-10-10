package qa.gorest.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class UsersTest {
    @Test
    public void listAllUsersTest(){
        RestAssured.baseURI = "https://gorest.co.in";

        RestAssured.given()
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);

        //How to extract response header field and parse response body
    }
}
