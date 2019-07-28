package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class View {

    @JsonProperty("binary_file")
    private File binaryFile;

    @JsonProperty("specification")
    private File specification;

    @JsonProperty("table_descriptions")
    private List<TableDescription> tableDescriptions;
}
