package qa.gorest.tests;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import qa.app.gorest.flows.GoRestCreateUser;
import qa.core.api.restclient.ResponseBodyParser;
import qa.core.asserts.Asserts;
import qa.core.report.TestNGListener;
import qa.core.utils.properties.FrameworkProperties;
import qa.core.utils.helper.RandomEmailGenerator;
import qa.core.utils.testdata.ReadTestData;

import java.util.*;

@Listeners(TestNGListener.class)
public class UsersDataDrivenTest extends BaseTest{

    @DataProvider(name = "create_user_test_data")
    public Object[][] createUserDataProvider() {

        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
        String createUserTestDataFileName = properties.getProperty("createUserTestData");
        String createUserTestDataFilePath = testDirName + testDataDirName + appName + "/" + createUserTestDataFileName;

        return ReadTestData.readTestData(createUserTestDataFilePath);
    }

    @Test(groups = {"data-driven"},dataProvider = "create_user_test_data")
    public void createUserTest(HashMap<String, String> testData) {
        String name = testData.get("first_name") + " " + testData.get("last_name");
        String email = RandomEmailGenerator.generateRandomEmail();

        Response createUserResponse = GoRestCreateUser.createUser(properties,name,email,testData.get("gender"),testData.get("status"));

        //Validate status code
        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate status code");


        String resourceURI = createUserResponse.header("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String userId = parser.get(parser.size()-1);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);

        Asserts.assertEquals(responseBodyParser.get("id").toString(),userId,"Validate id");
        Asserts.assertEquals(responseBodyParser.get("name"),name,"Validate name");
        Asserts.assertEquals(responseBodyParser.get("email"),email,"Validate email");
        Asserts.assertEquals(responseBodyParser.get("gender"),testData.get("gender"),"Validate gender");
        Asserts.assertEquals(responseBodyParser.get("status"),testData.get("status"),"Validate status");

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }
}
