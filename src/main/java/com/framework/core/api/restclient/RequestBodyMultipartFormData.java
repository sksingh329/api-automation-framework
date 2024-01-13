package com.framework.core.api.restclient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RequestBodyMultipartFormData {
    private final Map<String,String> textFormData;
    private final Map<String, File> fileFormData;

    public RequestBodyMultipartFormData(){
        textFormData = new HashMap<>();
        fileFormData = new HashMap<>();
    }

    public void setTextFormData(String textFormKey, String textFormValue) {
        textFormData.put(textFormKey,textFormValue);
    }
    public void setFileFormData(String fileFormKey, File fileFormValue){
        fileFormData.put(fileFormKey,fileFormValue);
    }

    public Map<String, String> getTextFormData() {
        return textFormData;
    }

    public Map<String, File> getFileFormData() {
        return fileFormData;
    }
}
