package trading.pro.dto;

import lombok.Data;

@Data
public class RequestResponseType {
    private String responseCode;
    private String responseMessage;
    private String response;
}
