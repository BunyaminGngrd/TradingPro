package trading.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import trading.pro.dto.StrategyResponse;
import trading.pro.entity.StrategyResponseEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StrategyResponseMapper {
    @Mappings({
            @Mapping(source = "stockCode", target = "stockCode"),
            @Mapping(source = "strategyResultMessage", target = "strategyResultMessage"),
            @Mapping(source = "buySignal", target = "buySignal")
    })
    StrategyResponse toStrategyResponse(StrategyResponseEntity strategyResponseEntity);

    List<StrategyResponse> toStrategyResponseList(List<StrategyResponseEntity> strategyResponseEntityList);
}
