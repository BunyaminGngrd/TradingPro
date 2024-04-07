package trading.pro.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes;
import trading.pro.dto.RequestResponseType;
import trading.pro.repository.GnlExtLogPerformanceRepository;
import trading.pro.service.IBaseService;
import trading.pro.service.ICatchGnlExtLogPerformanceService;

@Service
public class CatchGnlExtLogPerformanceServiceImpl implements ICatchGnlExtLogPerformanceService {
    private final GnlExtLogPerformanceRepository repository;
    private final IBaseService baseService;

    @Autowired
    public CatchGnlExtLogPerformanceServiceImpl(GnlExtLogPerformanceRepository repository, IBaseService baseService) {
        this.repository = repository;
        this.baseService = baseService;
    }

    @Override
    public RequestResponseType catchClear() {
        try {
            repository.deleteAll();
            return this.baseService.createResponseMessage(GnlEnumTypes.ResponseCode.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return this.baseService.createResponseMessage(GnlEnumTypes.ResponseCode.ERROR.getValue());
        }
    }
}
