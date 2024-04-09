package trading.pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponseDTO {
    private boolean success;
    private List<NewsItemDTO> result;
}
