package com.github.peacetrue.learn.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static com.github.peacetrue.learn.spring.RestTemplateController.RequestBodyBean;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void requestBody() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestBodyBean> entity = new HttpEntity<>(new RequestBodyBean(), headers);
        RequestBodyBean requestBodyBean = this.restTemplate.postForObject("/requestBody", entity, RequestBodyBean.class);
        Assert.assertEquals(requestBodyBean, entity.getBody());

    }

    @Test
    public void name() throws Exception {
        String json = "{\"data\":\"{\\\"c\\\":\\\"3\\\",\\\"b\\\":\\\"2\\\",\\\"a\\\":\\\"1\\\"}\",\"appId\":\"demo\",\"sign\":\"102A7048CAF22B9AA877B6D2FA7DCF58\"}";
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedHashMap linkedHashMap = objectMapper.readValue(json, LinkedHashMap.class);
        Object data = linkedHashMap.get("data");
        System.out.println(data);
    }
}
