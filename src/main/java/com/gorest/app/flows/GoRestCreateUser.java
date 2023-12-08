package com.gorest.app.flows;

import io.restassured.response.Response;
import com.gorest.app.pojo.UserPOJO;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.utils.properties.PropertiesUtils;

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
