package com.github.peacetrue.learn.spring;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiayx
 */
@RestController
@RequestMapping
public class RestTemplateController {

    @Data
    public static class RequestBodyBean {
        private String name;
    }

    @RequestMapping("/requestBody")
    public RequestBodyBean requestBody(@RequestBody RequestBodyBean bean) {
        return bean;
    }

}
