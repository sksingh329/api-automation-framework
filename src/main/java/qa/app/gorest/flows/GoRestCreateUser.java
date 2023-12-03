package qa.app.gorest.flows;

import io.restassured.response.Response;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.api.restclient.RequestParam;
import qa.core.utils.properties.PropertiesUtils;

public class GoRestCreateUser {
    public static Response createUser(PropertiesUtils properties, String name, String email, String gender, String status){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        request.setRequestHeaders("Authorization","Bearer "+properties.getProperty("apiKey"));


        UserPOJO user = new UserPOJO(name, email, gender, status);
        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Authorization","Bearer "+properties.getProperty("apiKey"));
        request.setRequestBody(user);
        return request.createRequest().post();
    }
}
