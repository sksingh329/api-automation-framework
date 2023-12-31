package com.practice.auth.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseBodyParser;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Listeners(TestNGListener.class)
public class AmadeusAuthTest {
    private RequestParam request;
    private String baseUri = "https://test.api.amadeus.com/v1";
    private String accessToken;

    @BeforeMethod
    public void getAccessToken(){
        Map<String,String> formParams = new HashMap<>();
        formParams.put("client_id","obowzRYwIKXz4YKG93FdkzTj0JWnikjn");
        formParams.put("client_secret","uhnIe3HSEpckNTev");
        formParams.put("grant_type","client_credentials");

        RequestParam accessTokenRequestParam = new RequestParam(baseUri,"/security/oauth2/token");
        accessTokenRequestParam.setFormParams(formParams);

        ResponseFetcher response= accessTokenRequestParam.createRequest().post();
        ResponseBodyParser responseBodyParser = response.getResponseBodyParser();
        accessToken = responseBodyParser.get("access_token");
    }
    @Test
    public void getFlightDetails(){
        String flightOrigin = "DEL";
        int flightMaxPrice = 20000;
        String flightCurrency = "INR";

        request = new RequestParam(baseUri,"/shopping/flight-destinations");
        request.setQueryParams("origin",flightOrigin);
        request.setQueryParams("maxPrice",flightMaxPrice);

        request.setRequestHeaders("Authorization","Bearer "+accessToken);

        ResponseFetcher response = request.createRequest().get();
        Asserts.assertEquals(response.getStatusCode(),200,"Validate status code");

        ResponseBodyParser responseBodyParser = response.getResponseBodyParser();
        //Validate origin and price range
        int dataSize = responseBodyParser.getList("data").size();

        Asserts.assertEquals(responseBodyParser.get("meta.currency"),flightCurrency,"Validate flight currency is "+flightCurrency);

        for(int i = 0; i < dataSize; i++){
            String actualFlightOrigin = responseBodyParser.get("data["+i+"].origin");
            float actualFlightPrice = Float.parseFloat(responseBodyParser.get("data["+i+"].price.total"));
            Asserts.assertEquals(actualFlightOrigin,flightOrigin,"Validate flight origin is "+flightOrigin);
            Asserts.assertTrue(actualFlightPrice<=flightMaxPrice,"Validate flight price is below "+flightMaxPrice);
        }
    }
}
