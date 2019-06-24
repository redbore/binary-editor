package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Field {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("value")
    private String value;
}
