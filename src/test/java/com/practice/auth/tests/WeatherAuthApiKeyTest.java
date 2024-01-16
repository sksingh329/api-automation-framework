package com.practice.auth.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.JsonParser;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListener.class)
public class WeatherAuthApiKeyTest {
    private RequestParam request;

    @BeforeMethod
    public void setup(){
        request = new RequestParam("http://api.weatherapi.com","/v1/current.json");
    }

    @Test
    public void getWeatherDetailsUsingApiKeyAsQueryParamTest(){
        String cityName = "Pune";
        request.setQueryParams("q",cityName);
        request.setQueryParams("aqi","no");
        request.setQueryParams("Key","1d791fbc051e4476b7a144221232106");

        ResponseFetcher weatherDetailsResponse = request.createRequest().get();

        JsonParser responseBodyParser = weatherDetailsResponse.getJsonParser();

        Asserts.assertEquals(responseBodyParser.get("location.name").toString(),cityName,"Validate city name");
    }
    @Test
    public void getWeatherDetailsUsingApiKeyAsRequestHeaderTest(){
        String cityName = "Pune";
        request.setQueryParams("q",cityName);
        request.setQueryParams("aqi","no");
        request.setRequestHeaders("Key","1d791fbc051e4476b7a144221232106");

        ResponseFetcher weatherDetailsResponse = request.createRequest().get();

        JsonParser responseBodyParser = weatherDetailsResponse.getJsonParser();

        Asserts.assertEquals(responseBodyParser.get("location.name").toString(),cityName,"Validate city name");
    }
}
