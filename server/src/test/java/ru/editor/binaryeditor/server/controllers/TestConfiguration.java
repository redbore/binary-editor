package ru.editor.binaryeditor.server.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class TestConfiguration {

    @Bean
    public RestOperations restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
        return restTemplate;
    }

    @Bean
    public EditorClient editorClient(RestOperations restTemplate) {
        return new EditorClient(restTemplate);
    }

    @Bean
    public EditorOperationsVerifier editorOperationsVerifier(EditorClient editorClient) {
        return new EditorOperationsVerifier(editorClient);
    }
}
