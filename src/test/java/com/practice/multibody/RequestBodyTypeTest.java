package com.practice.multibody;

import com.framework.core.api.constants.ContentType;
import com.framework.core.api.constants.HttpStatusCode;
import com.framework.core.api.restclient.RequestBodyMultipartFormData;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

@Listeners(TestNGListener.class)
public class RequestBodyTypeTest {
    private RequestParam request;

    @BeforeMethod
    public void setup(){
        request = new RequestParam("http://httpbin.org","/post");
    }

    @Test
    public void requestBodyWithText(){
        String bodyText = "This is a test string.";
        request.setRequestHeaders("Content-Type", ContentType.TEXT_PLAIN.getContentType());
        request.setRequestBody(bodyText);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        Asserts.assertContains(response.getResponseBodyParser().get("headers.Content-Type").toString(),"text/plain","Validate Content type");
        Asserts.assertEquals(response.getResponseBodyParser().get("headers.Host").toString(),"httpbin.org","Validate host");
    }
    @Test
    public void requestBodyWithJS(){
        String bodyText = "function login(){\n" +
                "    let x = 10;\n" +
                "    let y = 20;\n" +
                "    console.log(x+y);\n" +
                "}";
        request.setRequestHeaders("Content-Type",ContentType.APPLICATION_JS.getContentType());
        request.setRequestBody(bodyText);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        Asserts.assertContains(response.getResponseBodyParser().get("headers.Content-Type").toString(),"application/javascript","Validate Content type");
        Asserts.assertEquals(response.getResponseBodyParser().get("headers.Host").toString(),"httpbin.org","Validate host");
    }
    @Test
    public void requestBodyWithHtml(){
        String bodyText = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <title> \"Test\" </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    \n" +
                "    </body>\n" +
                "</html>";
        request.setRequestHeaders("Content-Type",ContentType.TEXT_HTML.getContentType());
        request.setRequestBody(bodyText);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        Asserts.assertContains(response.getResponseBodyParser().get("headers.Content-Type").toString(),"text/html","Validate Content type");
        Asserts.assertEquals(response.getResponseBodyParser().get("headers.Host").toString(),"httpbin.org","Validate host");
    }
    @Test
    public void requestBodyWithXml(){
        String bodyText = "<note>\n" +
                "    <to>subodh</to>\n" +
                "    </note>";
        request.setRequestHeaders("Content-Type",ContentType.APPLICATION_XML.getContentType());
        request.setRequestBody(bodyText);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        Asserts.assertContains(response.getResponseBodyParser().get("headers.Content-Type").toString(),"application/xml","Validate Content type");
        Asserts.assertEquals(response.getResponseBodyParser().get("headers.Host").toString(),"httpbin.org","Validate host");
    }
    @Test
    public void multiPartFormDataTest(){
        //Add test for different combination of multipart form-data
        String username = "Subodh";
        String expectedFileField = "data:application/octet-stream";
        RequestBodyMultipartFormData formData = new RequestBodyMultipartFormData();
        //TODO - replace absolute path with relative path
        File imageFile = new File("/Users/subodhsingh/Desktop/subodh/automation/restAssured/practice/framework/api-automation-framework/src/test/resources/others/formdata_img1.jpeg");
        formData.setTextFormData("username",username);
        formData.setFileFormData("userfile",imageFile);
        request.setRequestHeaders("Content-Type",ContentType.MULTIPART_FORM_DATA.getContentType());
        request.setRequestBodyMultipartFormData(formData);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        String filesBody = response.getResponseBodyParser().get("files.userfile");
        String actualUsername = response.getResponseBodyParser().get("form.username");
        //TODO - Check why content is not logged in report
        Asserts.assertContains(filesBody,"data:application/octet-stream","Validate response body has files -> userfile");
        //System.out.println(filesBody);
        Asserts.assertEquals(actualUsername,username,"Validate response body has form -> name");
    }
    @Test
    public void requestBodyWithBinaryTest(){
        File imageFile = new File("/Users/subodhsingh/Desktop/subodh/automation/restAssured/practice/framework/api-automation-framework/src/test/resources/others/formdata_img1.jpeg");
        request.setRequestHeaders("Content-Type","image/jpeg");
        request.setRequestBodyFile(imageFile);
        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        String actualData = response.getResponseBodyParser().get("data");
        Asserts.assertContains(actualData,"data:application/octet-stream","Validate response body has data");
    }
    @Test
    public void requestBodyWithUrlEncodedTest(){
        request = new RequestParam("https://test.api.amadeus.com/v1","/security/oauth2/token");

        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("client_id","obowzRYwIKXz4YKG93FdkzTj0JWnikjn");
        formParams.put("client_secret","uhnIe3HSEpckNTev");
        formParams.put("grant_type","client_credentials");

        request.setRequestHeaders("Content-Type","application/x-www-form-urlencoded");
        request.setFormParams(formParams);

        ResponseFetcher response = request.createRequest().post();
        Asserts.assertEquals(response.getStatusCode(), HttpStatusCode.OK_200.getCode(), "Validate Status Code");
        Asserts.assertEquals(response.getResponseBodyParser().get("token_type"),"Bearer","Validate token type is Bearer");
    }
}
