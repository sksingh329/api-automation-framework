package com.framework.core.api.constants;

public enum ContentType {
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_JS("application/javascript"),
    MULTIPART_FORM_DATA("multipart/form-data");


    private final String contentType;
    ContentType(String contentType){
        this.contentType = contentType;
    }
    public String getContentType(){
        return this.contentType;
    }
}
