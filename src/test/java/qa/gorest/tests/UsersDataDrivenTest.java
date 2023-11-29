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
import qa.core.utils.CSVDataReader;
import qa.core.utils.RandomEmailGenerator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Listeners(TestNGListener.class)
public class UsersDataDrivenTest extends BaseTest{

    @DataProvider(name = "create_user_test_data")
    public Object[][] createUserDataProvider() {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test_data/gorest/create_user_test_data.csv")).getFile());
        return CSVDataReader.readCSV(file.getAbsolutePath());
    }

    @Test(groups = {"data-driven"},dataProvider = "create_user_test_data")
    public void createUserTest(String firstName, String lastName, String gender, String status) {
        String name = firstName + " " + lastName;
        String email = RandomEmailGenerator.generateRandomEmail();

        Response createUserResponse = GoRestCreateUser.createUser(properties,name,email,gender,status);

        //Validate status code
        Asserts.assertEquals(createUserResponse.statusCode(),201,"Validate status code");


        String resourceURI = createUserResponse.header("location");

        List<String> parser = Arrays.asList(resourceURI.split("/"));
        String userId = parser.get(parser.size()-1);

        ResponseBodyParser responseBodyParser = new ResponseBodyParser(createUserResponse);

        Asserts.assertEquals(responseBodyParser.get("id").toString(),userId,"Validate id");
        Asserts.assertEquals(responseBodyParser.get("name"),name,"Validate name");
        Asserts.assertEquals(responseBodyParser.get("email"),email,"Validate email");
        Asserts.assertEquals(responseBodyParser.get("gender"),gender,"Validate gender");
        Asserts.assertEquals(responseBodyParser.get("status"),status,"Validate status");

        // Validate Headers are not present
        Headers responseHeaders = createUserResponse.headers();
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-page"),"Validate header x-pagination-page is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-limit"),"Validate header x-pagination-limit is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-total"),"Validate header x-pagination-total is not present");
        Asserts.assertFalse(responseHeaders.hasHeaderWithName("x-pagination-pages"),"Validate header x-pagination-pages is not present");
    }
}
