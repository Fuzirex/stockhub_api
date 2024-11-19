package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Item;
import stock.hub.api.repository.InvoiceRepository;
import stock.hub.api.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ItemRepository itemRepository;

    public Page<InvoiceHistoryResponseDTO> findInvoiceHistory(InvoiceHistoryRequestDTO dto) {
        List<Invoice> allInvoices = invoiceRepository.findAll();
        List<Item> allItems = itemRepository.findAll();

        return null;
    }
}
