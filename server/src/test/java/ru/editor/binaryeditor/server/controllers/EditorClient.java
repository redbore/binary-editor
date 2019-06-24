package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import rest.*;

import java.util.List;
import java.util.UUID;

import static ru.editor.binaryeditor.server.controllers.Endpoint.*;

@RequiredArgsConstructor
public class EditorClient {

    private final RestOperations restTemplate;

    public void open(OpenFile openFile) {
        restTemplate.postForObject(OPEN, openFile, Void.class);
    }

    public File save() {
        return restTemplate.postForObject(SAVE, null, File.class);
    }

    public View view() {
        return restTemplate.getForObject(VIEW, View.class);
    }

    public List<Field> pagination(UUID tableDescriptionId, Long rowCount, Long pageNumber) {
        UriComponents uri = UriComponentsBuilder.fromUriString(PAGINATION)
                .queryParam("row_count", rowCount)
                .queryParam("page_number", pageNumber)
                .build();
        ResponseEntity<List<Field>> responseEntity = restTemplate
                .exchange(
                        uri.toUriString(),
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Field>>() {
                        },
                        tableDescriptionId
                );
        return responseEntity.getBody();
    }

    public void fieldEdit(UUID fieldId, String value) {
        restTemplate.put(FIELD_EDIT, new FieldEdit(value), fieldId);
    }
}
