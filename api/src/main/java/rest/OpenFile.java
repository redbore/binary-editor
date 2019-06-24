package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenFile {

    @JsonProperty("binary_file")
    private final File binaryFile;

    @JsonProperty("specification")
    private final File specification;
}
