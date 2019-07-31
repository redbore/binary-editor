package rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Row {

    @JsonProperty("fields")
    private List<Field> fields;
}
