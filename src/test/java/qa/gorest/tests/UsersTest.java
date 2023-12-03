package qa.gorest.tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.utils.helper.RandomEmailGenerator;

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
        Assert.assertEquals(listUserResponse.getHeader("Content-Type"),"application/json; charset=utf-8");
        Assert.assertEquals(listUserResponse.getHeader("x-pagination-page"),"1");
        System.out.println(listUserResponse.getHeader("x-pagination-limit"));
        System.out.println(listUserResponse.getHeader("x-pagination-total"));
        System.out.println(listUserResponse.getHeader("x-pagination-pages"));

        //Parsing response body
        JsonPath responseBodyJsonPath = listUserResponse.jsonPath();
        responseBodyJsonPath.setRootPath("[0].");
        System.out.println(responseBodyJsonPath.getString("id"));
        System.out.println(responseBodyJsonPath.getString("name"));
        System.out.println(responseBodyJsonPath.getString("email"));
        System.out.println(responseBodyJsonPath.getString("gender"));
        System.out.println(responseBodyJsonPath.getString("status"));
        responseBodyJsonPath.setRootPath("");

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
        Assert.assertEquals(createUserResponseJsonPath.get("name"),name);
        Assert.assertEquals(createUserResponseJsonPath.get("email"),email);
        Assert.assertEquals(createUserResponseJsonPath.get("gender"),gender);
        Assert.assertEquals(createUserResponseJsonPath.get("status"),status);

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));
    }
    @Test
    public void getUserTest(){
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

        JsonPath createUserResponseJsonPath = createUserResponse.jsonPath();
        int responseUserId = createUserResponseJsonPath.getInt("id");

        // GET User
        Response getUserResponse = RestAssured.given().log().all()
                .pathParam("id",responseUserId)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241")
                .get("{id}");

        getUserResponse.then().log().all();

        Assert.assertEquals(getUserResponse.statusCode(),200);

        // Validate Headers
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertEquals(responseHeaders.getValue("Content-Type"),"application/json; charset=utf-8");
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));

        JsonPath getUserResponseJsonPath = getUserResponse.jsonPath();
        Assert.assertEquals(getUserResponseJsonPath.getInt("id"),responseUserId);
        Assert.assertEquals(getUserResponseJsonPath.getString("name"),name);
        Assert.assertEquals(getUserResponseJsonPath.getString("email"),email);
        Assert.assertEquals(getUserResponseJsonPath.getString("gender"),gender);
        Assert.assertEquals(getUserResponseJsonPath.getString("status"),status);
    }

    @Test
    public void updateUserTest(){
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

        JsonPath createUserResponseJsonPath = createUserResponse.jsonPath();
        int responseUserId = createUserResponseJsonPath.getInt("id");

        //Update User
        String updatedName = "Updated User";
        UserPOJO updatedUser = new UserPOJO(updatedName,email,gender,status);

        Response updateUserResponse = RestAssured.given()
                .header("Content-Type","application/json")
                .header("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241")
                .body(updatedUser)
                .pathParam("id",responseUserId)
                .put("{id}");

        Assert.assertEquals(updateUserResponse.statusCode(),200);

        // Validate Headers
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertEquals(responseHeaders.getValue("Content-Type"),"application/json; charset=utf-8");
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));

        JsonPath getUserResponseJsonPath = updateUserResponse.jsonPath();
        Assert.assertEquals(getUserResponseJsonPath.getInt("id"),responseUserId);
        Assert.assertEquals(getUserResponseJsonPath.getString("name"),updatedName);
        Assert.assertEquals(getUserResponseJsonPath.getString("email"),email);
        Assert.assertEquals(getUserResponseJsonPath.getString("gender"),gender);
        Assert.assertEquals(getUserResponseJsonPath.getString("status"),status);
    }
    @Test
    public void deleteUserTest(){
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

        JsonPath createUserResponseJsonPath = createUserResponse.jsonPath();
        int responseUserId = createUserResponseJsonPath.getInt("id");

        //Delete User
        Response deleteUserResponse = RestAssured.given()
                .header("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241")
                .pathParam("id",responseUserId)
                .delete("{id}");

        Assert.assertEquals(deleteUserResponse.statusCode(),204);

        // Validate Headers
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertEquals(responseHeaders.getValue("Content-Type"),"application/json; charset=utf-8");
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));
    }
}
