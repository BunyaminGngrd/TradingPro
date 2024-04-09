package trading.pro.dto;

import lombok.Data;

@Data
public class NewsItemDTO {
    private String key;
    private String url;
    private String description;
    private String image;
    private String name;
    private String source;
    private String date;
}
