package com.framework.core.api.restclient;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.util.List;

public class XmlParser {
    private final XmlPath xmlPath;

    public XmlParser(Response response){
        xmlPath = response.xmlPath();
    }
    public void setRootPath(String rootPath){
        xmlPath.setRootPath(rootPath);
    }
    public void resetRootPath(){
        xmlPath.setRootPath("");
    }
    public <T>T get(String path){
        return xmlPath.get(path);
    }
    public <T> List<T> getList(String path){
        return xmlPath.getList(path);
    }
}
