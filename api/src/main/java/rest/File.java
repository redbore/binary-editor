package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class File {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("file_name")
    private final String name;

    @JsonProperty("file_body")
    private final int[] body;
}
