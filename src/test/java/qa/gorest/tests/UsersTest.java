package qa.gorest.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.utils.RandomEmailGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UsersTest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://gorest.co.in"; // set only once per application and get this value from app.env.properties file
        RestAssured.basePath = "/public/v2/users";
    }
    @Test
    public void listAllUsersTest(){
        Response listUserResponse = RestAssured.get();

        //Fetching response headers
        int paginationLimit = Integer.parseInt(listUserResponse.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(listUserResponse.getHeader("x-pagination-total"));
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

        //Get the count of users in response
        int userCount = responseBodyJsonPath.getList("$").size();
        System.out.println(userCount);

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }
    }
    @Test
    public void filterUserWithNameTest(){
        Response filterUserResponse = RestAssured.given()
                .queryParam("name","kumar")
                .get();

        JsonPath filterUserResponseJsonPath = filterUserResponse.jsonPath();

        List<String> userNames = filterUserResponseJsonPath.getList("name");
        System.out.println(userNames);
        for (String userName : userNames){
            Assert.assertTrue(userName.toLowerCase(Locale.ROOT).contains("kumar"));
        }

        int paginationLimit = Integer.parseInt(filterUserResponse.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(filterUserResponse.getHeader("x-pagination-total"));
        int userCount = filterUserResponseJsonPath.getList("$").size();

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }
    }
    @Test
    public void createUserTest(){
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        UserPOJO user = new UserPOJO(name,email,gender,status);

        Response createUserResponse = RestAssured.given()
                .header("Content-Type","application/json")
                .header("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241")
                .body(user)
                .post();

        createUserResponse.then().log().all();

        Assert.assertEquals(createUserResponse.statusCode(),201);
        String resourceURI = createUserResponse.header("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String id = parser.get(parser.size()-1);
        int userId = Integer.parseInt(id);

        JsonPath createUserResponseJsonPath = createUserResponse.jsonPath();
        int responseUserId = createUserResponseJsonPath.getInt("id");

        Assert.assertEquals(responseUserId,userId);
    }
}
