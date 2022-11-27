package com.ssafy.pomostamp.room.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Service
public class ResTemplateServiceImpl implements RestTemplateService{

    @Override
    public String createSession(Map<String,String> requestHeader,String data) throws JsonProcessingException {

        System.out.println("service 넘어왔다!!!!!!!!!!!!!");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        Iterator<String> keys = requestHeader.keySet().iterator();

        while(keys.hasNext()){
            String key = keys.next();
            System.out.println("Key : "+key);
            System.out.println(requestHeader.get(key));
            headers.set(key,requestHeader.get(key));
        }


        HttpEntity<String> entity = new HttpEntity<>(data,headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.exchange(requestHeader.get("openvidu_server_url"), HttpMethod.POST,entity,String.class);
        return result.getBody();
    }

    @Override
    public String createToken(Map<String, String> requestHeader, String data) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        System.out.println("service 넘어왔다!!!!!!!!!!!!!");


        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null,null,null);


        CloseableHttpClient httpClient= HttpClientBuilder.create().setSSLContext(context).build();

        HttpPost httpPost=new HttpPost(requestHeader.get("openvidu_server_url"));
        Iterator<String> keys = requestHeader.keySet().iterator();

        //1.header추가
        System.out.println("-----------------------------------------");
        while(keys.hasNext()){
            String key = keys.next();
            System.out.println("Key : "+key);
            System.out.println(requestHeader.get(key));
//            headers.set(key,requestHeader.get(key));
            httpPost.addHeader(key,requestHeader.get(key));
        }
        System.out.println("-------------------The End------------------");

        //2.body 추가
        httpPost.setEntity(new StringEntity(data));

        //3.환경설정
        URI uri = new URIBuilder(requestHeader.get("openvidu_server_url")).build();
        httpPost.setURI(uri);

        RequestConfig requestConfig = RequestConfig.DEFAULT;
        httpPost.setConfig(requestConfig);

        HttpResponse httpResponse=httpClient.execute(httpPost);

        String resultJson = EntityUtils.toString(httpResponse.getEntity());




        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

//        RestTemplate restTemplate = new RestTemplate(factory);
//        ResponseEntity<String> result = restTemplate.exchange(requestHeader.get("openvidu_server_url"), HttpMethod.POST,entity,String.class);
//        return result.getBody();
        return resultJson;
    }
}
