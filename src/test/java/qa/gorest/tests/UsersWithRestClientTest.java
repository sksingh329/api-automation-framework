package qa.gorest.tests;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.app.gorest.flows.GoRestCreateUser;
import qa.app.gorest.flows.GoRestResponseSpec;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.api.restclient.Request;
import qa.core.api.restclient.ResponseBodyParser;
import qa.core.utils.RandomEmailGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UsersWithRestClientTest {
    private Request request;
    private String baseUri = "https://gorest.co.in";
    private String basePath = "/public/v2/users";

    @BeforeMethod
    public void setup(){
        request = new Request(baseUri,basePath);
        request.setRequestHeaders("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241");
    }

    @Test
    public void listAllUsersTest(){
        Response response = request.createRequest().get();

        response.then().spec(GoRestResponseSpec.getResponseSpec());

        //Validate headers
        Assert.assertEquals(response.getHeader("x-pagination-page"),"1");
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-page"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-limit"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-total"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-pages"));

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(response);

        //Validate number of users on a result page
        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));
        //Get the count of users in response
        int userCount = responseBodyParser.getList("$").size();

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }

        //Validate user has fields
        responseBodyParser.setRootPath("[0].");
        Assert.assertTrue(responseBodyParser.get("id").toString().length() > 0);
        Assert.assertTrue(responseBodyParser.get("name").toString().length() > 0);
        Assert.assertTrue(responseBodyParser.get("email").toString().length() > 0);
        Assert.assertTrue(responseBodyParser.get("gender").toString().length() > 0);
        Assert.assertTrue(responseBodyParser.get("status").toString().length() > 0);
    }

    @Test
    public void filterUserWithNameTest(){
        String searchName = "kumar";
        request.setQueryParams("name",searchName);
        request.setQueryParams("gender","male");
        Response response = request.createRequest().get();

        response.then().spec(GoRestResponseSpec.getResponseSpec());

        //Validate headers
        Assert.assertEquals(response.getHeader("x-pagination-page"),"1");
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-page"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-limit"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-total"));
        Assert.assertTrue(response.headers().hasHeaderWithName("x-pagination-pages"));

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(response);

        //Validate number of users on a result page
        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));
        //Get the count of users in response
        int userCount = responseBodyParser.getList("$").size();

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }

        //Validate each user name contains kumar
        List<String> userNamesList = responseBodyParser.getList("name");
        for (String username: userNamesList) {
            Assert.assertTrue(username.toLowerCase(Locale.ROOT).contains(searchName));
        }

    }

    @Test
    public void createUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(name,email,gender,status);

        Assert.assertEquals(createUserResponse.statusCode(),201);
        String resourceURI = createUserResponse.header("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String userId = parser.get(parser.size()-1);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);

        Assert.assertEquals(responseBodyParser.get("id").toString(),userId);
        Assert.assertEquals(responseBodyParser.get("name"),name);
        Assert.assertEquals(responseBodyParser.get("email"),email);
        Assert.assertEquals(responseBodyParser.get("gender"),gender);
        Assert.assertEquals(responseBodyParser.get("status"),status);

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

        Response createUserResponse = GoRestCreateUser.createUser(name,email,gender,status);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        Response getUserResponse = request.createRequest().get("{id}");

        Assert.assertEquals(getUserResponse.statusCode(),200);

        responseBodyParser = new ResponseBodyParser(getUserResponse);

        Assert.assertEquals(responseBodyParser.get("id").toString(),responseUserId);
        Assert.assertEquals(responseBodyParser.get("name"),name);
        Assert.assertEquals(responseBodyParser.get("email"),email);
        Assert.assertEquals(responseBodyParser.get("gender"),gender);
        Assert.assertEquals(responseBodyParser.get("status"),status);

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));
    }
    @Test
    public void updateUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(name, email, gender, status);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        //Update User
        String updatedName = "Updated User";
        UserPOJO updatedUser = new UserPOJO(updatedName,email,gender,status);

        request.setRequestBody(updatedUser);

        Response updateUserResponse = request.createRequest().put("{id}");

        responseBodyParser = new ResponseBodyParser(updateUserResponse);

        Assert.assertEquals(responseBodyParser.get("id").toString(),responseUserId);
        Assert.assertEquals(responseBodyParser.get("name"),name);
        Assert.assertEquals(responseBodyParser.get("email"),email);
        Assert.assertEquals(responseBodyParser.get("gender"),gender);
        Assert.assertEquals(responseBodyParser.get("status"),status);

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"));
        Assert.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"));
    }

    @Test
    public void deleteUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(name, email, gender, status);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        Response deleteUserResponse = request.createRequest().delete("{id}");

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
