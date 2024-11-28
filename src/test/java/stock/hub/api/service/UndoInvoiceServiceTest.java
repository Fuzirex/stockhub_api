package stock.hub.api.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import stock.hub.api.model.dto.request.UndoInvoiceRequestDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.model.entity.pk.InvoicePK;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.model.type.StockStatusType;
import stock.hub.api.repository.InvoiceRepository;
import stock.hub.api.repository.StockRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UndoInvoiceServiceTest {

    @Autowired
    private UndoInvoiceService undoInvoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void setUpDatabase() {
        // Base database values used for this service are loaded from data-invoice-entry-service.sql
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data-undo-invoice-service.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @BeforeEach
    void setUpSecurityContextHolder() {
        // Setting the default user in the SecurityContextHolder, simulating session request
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new Dealer("89471892000112"), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Should delete the Invoice of type SALE")
    void undoSaleInvoiceOperation() {
        UndoInvoiceRequestDTO undoInvoiceRequestDTO = createUndoInvoiceRequestDTO(
                "555", "555",InvoiceOperationType.SALE, "89471892000112", "22222BBBBB33333CCC");
        undoInvoiceService.undoInvoiceOperation(undoInvoiceRequestDTO);

        Optional<Stock> stock = stockRepository.findById(undoInvoiceRequestDTO.getChassisNumber());
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(undoInvoiceRequestDTO.getInvoiceNumber())
                .series(undoInvoiceRequestDTO.getInvoiceSeries())
                .operationType(undoInvoiceRequestDTO.getOperationType())
                .dealer(new Dealer(undoInvoiceRequestDTO.getDealerCNPJ()))
                .build());

        assertThat(invoice.isEmpty()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.AVAILABLE);
    }

    @Test
    @DisplayName("Should delete the Invoice of type RETURN")
    void undoReturnInvoiceOperation() {
        UndoInvoiceRequestDTO undoInvoiceRequestDTO = createUndoInvoiceRequestDTO(
                "777", "777",InvoiceOperationType.RETURN, "89471892000112", "33333CCCCC44444DDD");
        undoInvoiceService.undoInvoiceOperation(undoInvoiceRequestDTO);

        Optional<Stock> stock = stockRepository.findById(undoInvoiceRequestDTO.getChassisNumber());
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(undoInvoiceRequestDTO.getInvoiceNumber())
                .series(undoInvoiceRequestDTO.getInvoiceSeries())
                .operationType(undoInvoiceRequestDTO.getOperationType())
                .dealer(new Dealer(undoInvoiceRequestDTO.getDealerCNPJ()))
                .build());

        assertThat(invoice.isEmpty()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.SOLD);
    }

    @Test
    @DisplayName("Should delete the Invoice of type TRANSFER")
    void undoTransferInvoiceOperation() {
        UndoInvoiceRequestDTO undoInvoiceRequestDTO = createUndoInvoiceRequestDTO(
                "888", "888",InvoiceOperationType.TRANSFER, "89471892000112", "44444DDDDD55555EEE");
        undoInvoiceService.undoInvoiceOperation(undoInvoiceRequestDTO);

        Optional<Stock> stock = stockRepository.findById(undoInvoiceRequestDTO.getChassisNumber());
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(undoInvoiceRequestDTO.getInvoiceNumber())
                .series(undoInvoiceRequestDTO.getInvoiceSeries())
                .operationType(undoInvoiceRequestDTO.getOperationType())
                .dealer(new Dealer(undoInvoiceRequestDTO.getDealerCNPJ()))
                .build());

        assertThat(invoice.isEmpty()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.AVAILABLE);
        assertThat(stock.get().getDealer().getCnpj()).isEqualTo(undoInvoiceRequestDTO.getDealerCNPJ());
    }

    private UndoInvoiceRequestDTO createUndoInvoiceRequestDTO(String invoiceNumber, String invoiceSeries,
                                                              InvoiceOperationType operation, String dealerCNPJ,
                                                              String chassisNumber) {
        return UndoInvoiceRequestDTO.builder()
                .invoiceNumber(invoiceNumber)
                .invoiceSeries(invoiceSeries)
                .operationType(operation)
                .dealerCNPJ(dealerCNPJ)
                .chassisNumber(chassisNumber)
                .build();
    }
}