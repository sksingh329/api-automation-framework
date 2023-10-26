package qa.gorest.tests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.api.restclient.Request;

public class GoRestTestHelper {
    public static Response createUser(String name, String email, String gender, String status){
        String baseUri = "https://gorest.co.in";
        String basePath = "/public/v2/users";

        Request request = new Request(baseUri,basePath);

        UserPOJO user = new UserPOJO(name, email, gender, status);
        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241");
        request.setRequestBody(user);
        Response response = request.createRequest().log().all().post();
        return response;
    }
    public static ResponseSpecification getResponseSpec(){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectHeader("Content-Type","application/json; charset=utf-8");
        ResponseSpecification responseSpecification = responseSpecBuilder.build();
        return responseSpecification;
    }
    public static ResponseSpecification postResponseSpec(){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(201);
        responseSpecBuilder.expectHeader("Content-Type","application/json; charset=utf-8");
        ResponseSpecification responseSpecification = responseSpecBuilder.build();
        return responseSpecification;
    }
}
