package qa.gorest.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.core.api.restclient.Request;
import qa.core.api.restclient.ResponseBody;
import qa.core.utils.RandomEmailGenerator;

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

        ResponseBody responseBody = new ResponseBody(response);

        //Validate number of users on a result page
        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));
        //Get the count of users in response
        int userCount = responseBody.getList("$").size();

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }

        //Validate user has fields
        responseBody.setRootPath("[0].");
        Assert.assertTrue(responseBody.get("id").toString().length() > 0);
        Assert.assertTrue(responseBody.get("name").toString().length() > 0);
        Assert.assertTrue(responseBody.get("email").toString().length() > 0);
        Assert.assertTrue(responseBody.get("gender").toString().length() > 0);
        Assert.assertTrue(responseBody.get("status").toString().length() > 0);
    }

    @Test
    public void filterUserWithNameTest(){
        request.setQueryParams("name","kumar");
        request.setQueryParams("gender","male");
        Response response = request.createRequest().get();

        response.then().spec(GoRestTestHelper.getResponseSpec());
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

        ResponseBody responseBody = new ResponseBody(createUserResponse);
        int responseUserId = responseBody.get("id");

        request.setPathParams("id", String.valueOf(responseUserId));

        Response response = request.createRequest().get("{id}");

        Assert.assertEquals(response.statusCode(),200);
    }
}
