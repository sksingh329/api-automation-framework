package com.gorest.tests;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.gorest.app.flows.GoRestCreateUser;
import com.gorest.app.pojo.UserPOJO;
import com.framework.core.asserts.Asserts;
import com.framework.core.api.restclient.ResponseBodyParser;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.helper.RandomEmailGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Listeners(TestNGListener.class)
public class UsersWithRestClientTest extends BaseTest{

    @Test(groups = {"smoke","regression"} )
    public void listAllUsersTest(){
        Response response = request.createRequest().get();

        //Validate status code
        Asserts.assertEquals(response.statusCode(),200,"Validate status code");

        //Validate headers
        Asserts.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8","Validate Content-Type");
        Asserts.assertEquals(response.getHeader("x-pagination-page"),"1","Validate Response Header - x-pagination-page");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-total exist");

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(response);

        //Validate number of users on a result page
        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));
        //Get the count of users in response
        int userCount = responseBodyParser.getList("$").size();

        if(paginationTotal > 10){
            Asserts.assertEquals(userCount,paginationLimit,"Validate userCount is same as paginationLimit when total user count is greater than 10");
        }
        else{
            Asserts.assertEquals(userCount,paginationTotal,"Validate userCount is same as paginationTotal when total user count is less than 10");
        }

        //Validate user has fields
        responseBodyParser.setRootPath("[0].");
        Asserts.assertTrue(responseBodyParser.get("id").toString().length() > 0,"Validate response body has id field");
        Asserts.assertTrue(responseBodyParser.get("name").toString().length() > 0,"Validate response body has name field");
        Asserts.assertTrue(responseBodyParser.get("email").toString().length() > 0,"Validate response body has email field");
        Asserts.assertTrue(responseBodyParser.get("gender").toString().length() > 0,"Validate response body has gender field");
        Asserts.assertTrue(responseBodyParser.get("status").toString().length() > 0,"Validate response body has status");
    }

    @Test(groups = {"regression"} )
    public void filterUserWithNameTest(){
        String searchName = "kumar";
        request.setQueryParams("name",searchName);
        request.setQueryParams("gender","male");
        Response response = request.createRequest().get();

        //Validate status code
        Asserts.assertEquals(response.statusCode(),200,"Validate status code");

        //Validate headers
        Asserts.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8","Validate Content-Type");
        Asserts.assertEquals(response.getHeader("x-pagination-page"),"1","Validate Response Header - x-pagination-page");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total exist");
        Asserts.assertTrue(response.headers().hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-total exist");

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(response);

        //Validate number of users on a result page
        int paginationLimit = Integer.parseInt(response.getHeader("x-pagination-limit"));
        int paginationTotal = Integer.parseInt(response.getHeader("x-pagination-total"));
        //Get the count of users in response
        int userCount = responseBodyParser.getList("$").size();

        if(paginationTotal > 10){
            Asserts.assertEquals(userCount,paginationLimit,"Validate userCount is same as paginationLimit when total user count is greater than 10");
        }
        else{
            Asserts.assertEquals(userCount,paginationTotal,"Validate userCount is same as paginationTotal when total user count is less than 10");
        }

        //Validate each user name contains kumar
        List<String> userNamesList = responseBodyParser.getList("name");
        for (String username: userNamesList) {
            Asserts.assertContains(username.toLowerCase(Locale.ROOT),searchName.toLowerCase(Locale.ROOT),"Validate user name contains "+searchName);
        }

    }

    @Test(groups = {"regression"} )
    public void createUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(properties,name,email,gender,status);

        //Validate status code
        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate status code");


        String resourceURI = createUserResponse.header("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String userId = parser.get(parser.size()-1);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);

        Asserts.assertEquals(responseBodyParser.get("id").toString(),userId,"Validate id");
        Asserts.assertEquals(responseBodyParser.get("name"),name,"Validate name");
        Asserts.assertEquals(responseBodyParser.get("email"),email,"Validate email");
        Asserts.assertEquals(responseBodyParser.get("gender"),gender,"Validate gender");
        Asserts.assertEquals(responseBodyParser.get("status"),status,"Validate status");

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }

    @Test(groups = {"regression"} )
    public void getUserTest(){
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(properties,name,email,gender,status);

        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate Status Code");

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        Response getUserResponse = request.createRequest().get("{id}");

        //Validate status code
        Asserts.assertEquals(getUserResponse.statusCode(),200,"Validate status code");

        responseBodyParser = new ResponseBodyParser(getUserResponse);

        Asserts.assertEquals(responseBodyParser.get("id").toString(),responseUserId,"Validate id");
        Asserts.assertEquals(responseBodyParser.get("name"),name,"Validate name");
        Asserts.assertEquals(responseBodyParser.get("email"),email,"Validate email");
        Asserts.assertEquals(responseBodyParser.get("gender"),gender,"Validate gender");
        Asserts.assertEquals(responseBodyParser.get("status"),status,"Validate status");

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }
    @Test(groups = {"regression"} )
    public void updateUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(properties,name, email, gender, status);

        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate Status Code");

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        //Update User
        String updatedName = "Updated User";
        UserPOJO updatedUser = new UserPOJO(updatedName,email,gender,status);

        request.setRequestBody(updatedUser);

        Response updateUserResponse = request.createRequest().put("{id}");

        //Validate status code
        Asserts.assertEquals(updateUserResponse.statusCode(),200,"Validate status code");

        responseBodyParser = new ResponseBodyParser(updateUserResponse);

        Asserts.assertEquals(responseBodyParser.get("id").toString(),responseUserId,"Validate id");
        Asserts.assertEquals(responseBodyParser.get("name"),name,"Validate name");
        Asserts.assertEquals(responseBodyParser.get("email"),email,"Validate email");
        Asserts.assertEquals(responseBodyParser.get("gender"),gender,"Validate gender");
        Asserts.assertEquals(responseBodyParser.get("status"),status,"Validate status");

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }

    @Test(groups = {"regression"} )
    public void deleteUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        Response createUserResponse = GoRestCreateUser.createUser(properties,name, email, gender, status);

        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate Status Code");

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        Response deleteUserResponse = request.createRequest().delete("{id}");

        //Validate status code
        Asserts.assertEquals(deleteUserResponse.statusCode(),204,"Validate status code");

        // Validate Headers
        Headers responseHeaders = createUserResponse.headers();
        Assert.assertEquals(responseHeaders.getValue("Content-Type"),"application/json; charset=utf-8");

        // Validate Headers are not present
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }
}
