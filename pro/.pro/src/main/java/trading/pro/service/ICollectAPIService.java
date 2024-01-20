package trading.pro.service;

import trading.pro.dto.LiveDataResponseDTO;
import trading.pro.entity.LiveDataEntity;

public interface ICollectAPIService {
    LiveDataResponseDTO getAllData();

    LiveDataEntity findByCode(String code);
}
