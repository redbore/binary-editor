package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
@Builder
public class TableDescription {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("field_count")
    private long rowCount;

    @JsonProperty("column_names")
    private final List<String> columnNames;

    public static TableDescription.TableDescriptionBuilder tableDescription() {
        return builder();
    }
}
