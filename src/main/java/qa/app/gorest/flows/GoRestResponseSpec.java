package qa.app.gorest.flows;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class GoRestResponseSpec {
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
