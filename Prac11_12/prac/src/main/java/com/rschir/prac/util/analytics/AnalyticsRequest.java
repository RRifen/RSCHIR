package com.rschir.prac.util.analytics;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AnalyticsRequest {
    public void callAnalyticsRequest(String objectValue) {
        String url = "http://python:8000/analytics";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("object", objectValue);

        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        restTemplate.postForLocation(url, request);
    }
}
