package qa.gorest.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.core.api.restclient.Request;
import qa.core.api.restclient.ResponseBodyParser;
import qa.core.utils.RandomEmailGenerator;

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

        response.then().spec(GoRestTestHelper.getResponseSpec());

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

        response.then().spec(GoRestTestHelper.getResponseSpec());

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

        Response createUserResponse = GoRestTestHelper.createUser(name,email,gender,status);

        Assert.assertEquals(createUserResponse.statusCode(),201);
    }

    @Test
    public void getUserTest(){
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestTestHelper.createUser(name,email,gender,status);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        int responseUserId = responseBodyParser.get("id");

        request.setPathParams("id", String.valueOf(responseUserId));

        Response response = request.createRequest().get("{id}");

        Assert.assertEquals(response.statusCode(),200);
    }
}
