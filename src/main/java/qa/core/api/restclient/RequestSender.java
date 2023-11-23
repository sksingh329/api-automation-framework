package qa.core.api.restclient;

import io.restassured.http.Headers;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qa.core.report.ReportLevel;
import qa.core.report.ReporterUtils;

public class RequestSender {
    private RequestSpecification requestSpec;
    public RequestSender(RequestSpecification requestSpec){
        this.requestSpec = requestSpec;
    }
    public Response get(){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","GET");
        Response response = requestSpec.get();
        logResponse(response);
        return response;
    }
    public Response get(String path){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","GET - " + path);
        Response response = requestSpec.get(path);
        logResponse(response);
        return response;
    }
    public Response post(){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","POST");
        Response response = requestSpec.post();
        logResponse(response);
        return response;
    }
    public Response post(String path){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","POST - " + path);
        Response response = requestSpec.post(path);
        logResponse(response);
        return response;
    }
    public Response put(){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","PUT");
        Response response = requestSpec.put();
        logResponse(response);
        return response;
    }
    public Response put(String path){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","PUT - " + path);
        Response response = requestSpec.put(path);
        logResponse(response);
        return response;
    }
    public Response patch(){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","PATCH");
        Response response = requestSpec.patch();
        logResponse(response);
        return response;
    }
    public Response patch(String path){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","PATCH - " +path);
        Response response = requestSpec.patch(path);
        logResponse(response);
        return response;
    }
    public Response delete(){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","DELETE");
        Response response = requestSpec.delete();
        logResponse(response);
        return response;
    }
    public Response delete(String path){
        ReporterUtils.log(ReportLevel.INFO,"Request Method","DELETE - " + path);
        Response response = requestSpec.delete(path);
        logResponse(response);
        return response;
    }
    private void logResponse(Response response){
        ReporterUtils.log(ReportLevel.INFO, "Response Status Line",response.statusLine());
        Headers responseHeaders = response.getHeaders();
        for (Header header: responseHeaders) {
            ReporterUtils.log(ReportLevel.INFO,"Response Headers", header.getName() + " : " + header.getValue());
        }
        if (!response.getBody().asString().isEmpty()){
            ReporterUtils.log(ReportLevel.INFO,"Response Body", response.asPrettyString());
        }
        else{
            ReporterUtils.log(ReportLevel.INFO,"Response Body is empty");
        }
    }
}
