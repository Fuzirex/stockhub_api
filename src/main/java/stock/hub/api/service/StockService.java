package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.StockRequestDTO;
import stock.hub.api.model.dto.response.StockResponseDTO;
import stock.hub.api.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Page<StockResponseDTO> findDealerStock(StockRequestDTO dto) {
        return stockRepository.findDealerStockByFilter(
                dto.getDealerCNPJ(),
                StringUtils.defaultIfBlank(dto.getProductType(), null),
                StringUtils.defaultIfBlank(dto.getModel(), null),
                StringUtils.defaultIfBlank(dto.getItemCode(), null),
                StringUtils.defaultIfBlank(dto.getCommercialSeries(), null),
                StringUtils.defaultIfBlank(dto.getChassisNumber(), null),
                PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("chassisNumber")));
    }
}
