package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LiveDataResponseDTO {
    private String success;
    private List<LiveDataResult> result;

    @JsonCreator
    public LiveDataResponseDTO(@JsonProperty("success") String success,
                               @JsonProperty("result") List<LiveDataResult> result) {
        this.success = success;
        this.result = result;
    }
}
