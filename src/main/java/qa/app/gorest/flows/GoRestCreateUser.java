package qa.app.gorest.flows;

import io.restassured.response.Response;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.api.restclient.Request;

public class GoRestCreateUser {
    public static Response createUser(String name, String email, String gender, String status){
        String baseUri = "https://gorest.co.in";
        String basePath = "/public/v2/users";

        Request request = new Request(baseUri,basePath);

        UserPOJO user = new UserPOJO(name, email, gender, status);
        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Authorization","Bearer ce18e719571db0642120abcee05b7607754782c82ed7fdcd8b78c40a6bccf241");
        request.setRequestBody(user);
        Response response = request.createRequest().post();
        return response;
    }
}
