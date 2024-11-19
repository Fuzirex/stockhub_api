package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.model.dto.response.InvoiceOperationTypeResponseDTO;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.repository.ItemRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ItemRepository itemRepository;

    public Page<InvoiceHistoryResponseDTO> findInvoiceHistory(InvoiceHistoryRequestDTO dto) {
        return itemRepository.findInvoiceHistoryByFilter(
                dto.getDealerCNPJ(),
                dto.getEmissionPeriod(),
                StringUtils.defaultIfBlank(dto.getInvoiceNumber(), null),
                StringUtils.defaultIfBlank(dto.getProductType(), null),
                StringUtils.defaultIfBlank(dto.getProductModel(), null),
                StringUtils.defaultIfBlank(dto.getItemCode(), null),
                StringUtils.defaultIfBlank(dto.getCommercialSeries(), null),
                StringUtils.defaultIfBlank(dto.getChassisNumber(), null),
                Objects.nonNull(dto.getOperationType()) ? InvoiceOperationType.getInvoiceOperationType(dto.getOperationType()) : null,
                PageRequest.of(dto.getPage(), dto.getSize(),
                        Sort.by(Sort.Direction.DESC, "pk.invoice.emissionDate")));
    }

    public List<InvoiceOperationTypeResponseDTO> getInvoiceOperationTypes() {
        return EnumSet.allOf(InvoiceOperationType.class).stream().map(InvoiceOperationTypeResponseDTO::new).toList();
    }
}
