package com.gorest.tests;

import com.framework.core.api.constants.HttpStatusCode;
import com.framework.core.api.restclient.ResponseFetcher;
import com.gorest.app.flows.GoRestValidation;
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
        ResponseFetcher response = request.createRequest().get();

        //Validate status code
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(),"Validate status code");

        //Validate headers
        GoRestValidation.validateConTentType(response);
        GoRestValidation.validatePaginationHeaders(response);

        ResponseBodyParser responseBodyParser = response.getResponseBodyParser();

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
        GoRestValidation.validateUserFieldsResponseBody(responseBodyParser);
    }

    @Test(groups = {"regression"} )
    public void filterUserWithNameTest(){
        String searchName = "kumar";
        request.setQueryParams("name",searchName);
        request.setQueryParams("gender","male");
        ResponseFetcher response = request.createRequest().get();

        //Validate status code
        Asserts.assertEquals(response.getStatusCode(),HttpStatusCode.OK_200.getCode(), "Validate status code");

        //Validate headers
        GoRestValidation.validateConTentType(response);
        GoRestValidation.validatePaginationHeaders(response);

        ResponseBodyParser responseBodyParser = response.getResponseBodyParser();

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

        ResponseFetcher createUserResponse = GoRestCreateUser.createUser(properties,name,email,gender,status);

        //Validate status code
        Asserts.assertEquals(createUserResponse.getStatusCode(),HttpStatusCode.CREATED_201.getCode(), "Validate status code");
        
        String resourceURI = createUserResponse.getHeader("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String userId = parser.get(parser.size()-1);

        ResponseBodyParser responseBodyParser = createUserResponse.getResponseBodyParser();
        GoRestValidation.validateUserFieldsResponseBody(responseBodyParser);

        // Validate Headers are not present
        GoRestValidation.validatePaginationHeadersNotPresent(createUserResponse);
    }

    @Test(groups = {"regression"} )
    public void getUserTest(){
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        ResponseFetcher createUserResponse = GoRestCreateUser.createUser(properties,name,email,gender,status);

        Asserts.assertEquals(createUserResponse.getStatusCode(),HttpStatusCode.CREATED_201.getCode(), "Validate Status Code");

        ResponseBodyParser responseBodyParser = createUserResponse.getResponseBodyParser();
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        ResponseFetcher getUserResponse = request.createRequest().get("{id}");

        //Validate status code
        Asserts.assertEquals(getUserResponse.getStatusCode(),HttpStatusCode.OK_200.getCode(), "Validate status code");

        responseBodyParser = getUserResponse.getResponseBodyParser();

        GoRestValidation.validateUserFieldsResponseBody(responseBodyParser);

        // Validate Headers are not present
        GoRestValidation.validatePaginationHeadersNotPresent(createUserResponse);
    }
    @Test(groups = {"regression"} )
    public void updateUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        ResponseFetcher createUserResponse = GoRestCreateUser.createUser(properties,name, email, gender, status);

        Asserts.assertEquals(createUserResponse.getStatusCode(),HttpStatusCode.CREATED_201.getCode(), "Validate Status Code");

        ResponseBodyParser responseBodyParser = createUserResponse.getResponseBodyParser();
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        //Update User
        String updatedName = "Updated User";
        UserPOJO updatedUser = new UserPOJO(updatedName,email,gender,status);

        request.setRequestBody(updatedUser);

        ResponseFetcher updateUserResponse = request.createRequest().put("{id}");

        //Validate status code
        Asserts.assertEquals(updateUserResponse.getStatusCode(),HttpStatusCode.OK_200.getCode(), "Validate status code");

        responseBodyParser = updateUserResponse.getResponseBodyParser();

        GoRestValidation.validateUserFieldsResponseBody(responseBodyParser);

        // Validate Headers are not present
        GoRestValidation.validatePaginationHeadersNotPresent(createUserResponse);
    }

    @Test(groups = {"regression"} )
    public void deleteUserTest() {
        String name = "Test User";
        String email = RandomEmailGenerator.generateRandomEmail();
        String gender = "male";
        String status = "active";

        ResponseFetcher createUserResponse = GoRestCreateUser.createUser(properties,name, email, gender, status);

        Asserts.assertEquals(createUserResponse.getStatusCode(),HttpStatusCode.CREATED_201.getCode(), "Validate Status Code");

        ResponseBodyParser responseBodyParser = createUserResponse.getResponseBodyParser();
        String responseUserId = responseBodyParser.get("id").toString();

        request.setPathParams("id", String.valueOf(responseUserId));

        ResponseFetcher deleteUserResponse = request.createRequest().delete("{id}");

        //Validate status code
        Asserts.assertEquals(deleteUserResponse.getStatusCode(),HttpStatusCode.NO_CONTENT_204.getCode(), "Validate status code");

        // Validate Headers
        Headers responseHeaders = createUserResponse.getHeaders();
        Assert.assertEquals(responseHeaders.getValue("Content-Type"),"application/json; charset=utf-8");

        // Validate Headers are not present
        GoRestValidation.validatePaginationHeadersNotPresent(createUserResponse);
    }
}
