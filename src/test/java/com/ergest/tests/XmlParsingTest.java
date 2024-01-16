package com.ergest.tests;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.report.TestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListener.class)
public class XmlParsingTest {
    private RequestParam request;

    @BeforeMethod
    public void setup(){
        request = new RequestParam("http://ergast.com","/api/f1/2016/circuits.xml");
    }

    @Test
    public void circuitNameListTest(){
        ResponseFetcher response = request.createRequest().get();
        System.out.println(response.getResponseBody());
    }
}
