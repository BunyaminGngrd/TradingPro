package trading.pro.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.dto.LiveDataResponseDTO;
import trading.pro.entity.LiveDataEntity;
import trading.pro.mapper.LiveDataMapper;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.ICollectAPIService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CollectAPIServiceImpl implements ICollectAPIService {

    private final String apiKey;
    private final LiveDataRepository liveDataRepository;
    private final LiveDataMapper mapper = Mappers.getMapper(LiveDataMapper.class);

    @Autowired
    public CollectAPIServiceImpl(String apiKey, LiveDataRepository liveDataRepository) {
        this.apiKey = apiKey;
        this.liveDataRepository = liveDataRepository;
    }

    @LogPerformance
    @Override
    public LiveDataResponseDTO getAllData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpResponse<String> response = Unirest.get("https://api.collectapi.com/economy/hisseSenedi")
                    .header("content-type", "application/json")
                    .header("authorization", apiKey)
                    .asString();

            LiveDataResponseDTO liveDataResponseDTO = objectMapper.readValue(response.getBody(), LiveDataResponseDTO.class);
            this.saveLiveData(liveDataResponseDTO);
            return liveDataResponseDTO;
        } catch (Exception ex) {
            throw new RuntimeException("CollectAPIServiceImpl.getAllData().ERROR : " + ex.getMessage());
        }
    }

    @Override
    public LiveDataEntity findByCode(String code) {
        LiveDataEntity data = this.liveDataRepository.findByCode(code);
        return data != null ? data : null;
    }

    private void saveLiveData(LiveDataResponseDTO liveDataResponseDTO) {
        LocalDateTime currentDate = LocalDateTime.now();
        String formatCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<LiveDataEntity> entityList = this.mapper.toEntityList(liveDataResponseDTO.getResult());
        entityList.forEach(entity -> {
            entity.setDate(formatCurrentDate);
            liveDataRepository.save(entity);
        });
    }
}
