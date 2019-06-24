package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class File {

    @JsonProperty("file_name")
    private final String fileName;

    @JsonProperty("file_body")
    private final int[] fileBody;
}
