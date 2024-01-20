package trading.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trading.pro.dto.LiveDataResponseDTO;
import trading.pro.dto.LiveDataResult;
import trading.pro.entity.LiveDataEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LiveDataMapper {
    LiveDataEntity toEntity(LiveDataResult liveDataResult);

    LiveDataResult toDTO(LiveDataEntity liveDataEntity);

    List<LiveDataEntity> toEntityList(List<LiveDataResult> liveDataResultList);

    List<LiveDataResult> toDTOList(List<LiveDataEntity> liveDataEntityList);
}
