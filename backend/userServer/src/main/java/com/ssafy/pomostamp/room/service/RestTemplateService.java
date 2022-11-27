package com.ssafy.pomostamp.room.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface RestTemplateService {
    String createSession(Map<String,String> requestHeader,String data) throws JsonProcessingException;


    String createToken(Map<String, String> requestHeader, String data) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException;
}
