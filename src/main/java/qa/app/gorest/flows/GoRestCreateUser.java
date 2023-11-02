package qa.app.gorest.flows;

import io.restassured.response.Response;
import qa.app.gorest.pojo.UserPOJO;
import qa.core.api.restclient.Request;
import qa.core.utils.PropertiesUtils;

public class GoRestCreateUser {
    public static Response createUser(PropertiesUtils properties, String name, String email, String gender, String status){
        String baseUri = properties.getProperty("baseUri");
        String basePath = properties.getProperty("usersBasePath");

        Request request = new Request(baseUri,basePath);

        UserPOJO user = new UserPOJO(name, email, gender, status);
        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Authorization","Bearer "+properties.getProperty("apiKey"));
        request.setRequestBody(user);
        Response response = request.createRequest().post();
        return response;
    }
}
