package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class View {

    @JsonProperty("binary_file_name")
    private String binaryFileName;

    @JsonProperty("specification_name")
    private String specificationName;

    @JsonProperty("table_descriptions")
    private List<TableDescription> tableDescriptions;
}
