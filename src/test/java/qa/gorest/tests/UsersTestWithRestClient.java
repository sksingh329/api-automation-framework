package qa.gorest.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.core.api.restclient.Request;

public class UsersTestWithRestClient {
    private Request request;

    @BeforeMethod
    public void setup(){
        request = new Request("https://gorest.co.in","/public/v2/users");
    }

    @Test
    public void listAllUsersTest(){
        Response response = request.createRequest().log().all().get();

        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
        Assert.assertEquals(response.getHeader("x-pagination-page"),"1");

        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));

        //Parsing response body
        JsonPath responseBodyJsonPath = response.jsonPath();

        //Get the count of users in response
        int userCount = responseBodyJsonPath.getList("$").size();

        if(paginationTotal > 10){
            Assert.assertEquals(userCount,paginationLimit);
        }
        else{
            Assert.assertEquals(userCount,paginationTotal);
        }

    }
    @Test
    public void filterUserWithNameTest(){
        request.setQueryParam("name","kumar");
        request.setQueryParam("gender","male");
        Response response = request.createRequest().log().all().get();

        response.then().log().all();

        Assert.assertEquals(response.statusCode(),200);
    }
}
