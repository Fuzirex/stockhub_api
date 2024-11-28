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
import stock.hub.api.model.dto.request.InvoiceEntryRequestDTO;
import stock.hub.api.model.dto.request.InvoiceEntryStockRequestDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.model.entity.pk.InvoicePK;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.*;
import stock.hub.api.repository.InvoiceRepository;
import stock.hub.api.repository.StockRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InvoiceEntryServiceTest {

    @Autowired
    private InvoiceEntryService invoiceEntryService;

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
        populator.addScript(new ClassPathResource("data-invoice-entry-service.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @BeforeEach
    void setUpSecurityContextHolder() {
        // Setting the default user in the SecurityContextHolder, simulating session request
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new Dealer("37785316000146"), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Should save an Invoice of type SALE")
    void shouldSaveSaleInvoice() {
        InvoiceEntryRequestDTO invoiceEntryRequestDTO = createInvoiceEntryRequestDTO(
                "222", "222", InvoiceOperationType.SALE, "37785316000146", "00000JJJJJ11111KKK", null);
        invoiceEntryService.processInvoiceEntry(invoiceEntryRequestDTO);

        Optional<Stock> stock = stockRepository.findById("00000JJJJJ11111KKK");
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(invoiceEntryRequestDTO.getInvoiceNumber())
                .series(invoiceEntryRequestDTO.getInvoiceSeries())
                .operationType(invoiceEntryRequestDTO.getOperationType())
                .dealer(new Dealer(invoiceEntryRequestDTO.getDealerCNPJ()))
                .build());

        assertThat(invoice.isPresent()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.SOLD);
    }

    @Test
    @DisplayName("Should save an Invoice of type RETURN")
    void shouldSaveReturnInvoice() {
        InvoiceEntryRequestDTO invoiceEntryRequestDTO = createInvoiceEntryRequestDTO(
                "333", "333", InvoiceOperationType.RETURN, "37785316000146", "11111AAAAA22222BBB", null);
        invoiceEntryService.processInvoiceEntry(invoiceEntryRequestDTO);

        Optional<Stock> stock = stockRepository.findById("11111AAAAA22222BBB");
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(invoiceEntryRequestDTO.getInvoiceNumber())
                .series(invoiceEntryRequestDTO.getInvoiceSeries())
                .operationType(invoiceEntryRequestDTO.getOperationType())
                .dealer(new Dealer(invoiceEntryRequestDTO.getDealerCNPJ()))
                .build());

        assertThat(invoice.isPresent()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.AVAILABLE);
    }

    @Test
    @DisplayName("Should save an Invoice of type TRANSFER")
    void shouldSaveTransferInvoice() {
        InvoiceEntryRequestDTO invoiceEntryRequestDTO = createInvoiceEntryRequestDTO(
                "444", "444", InvoiceOperationType.TRANSFER, "37785316000146", "ZZZAA1111111111TTT", "89342675000122");
        invoiceEntryService.processInvoiceEntry(invoiceEntryRequestDTO);

        Optional<Stock> stock = stockRepository.findById("ZZZAA1111111111TTT");
        Optional<Invoice> invoice = invoiceRepository.findById(InvoicePK.builder()
                .number(invoiceEntryRequestDTO.getInvoiceNumber())
                .series(invoiceEntryRequestDTO.getInvoiceSeries())
                .operationType(invoiceEntryRequestDTO.getOperationType())
                .dealer(Dealer.builder().cnpj(invoiceEntryRequestDTO.getDealerCNPJ()).build())
                .build());

        assertThat(invoice.isPresent()).isTrue();
        assertThat(stock.isPresent()).isTrue();
        assertThat(stock.get().getStatus()).isEqualTo(StockStatusType.AVAILABLE);
        assertThat(stock.get().getDealer().getCnpj()).isEqualTo("89342675000122");
    }

    @Test
    @DisplayName("Should throw error Invoice Already Exists")
    void shouldThorwInvoiceAlreadyExists() {
        InvoiceEntryRequestDTO invoiceEntryRequestDTO = createInvoiceEntryRequestDTO("111", "111", InvoiceOperationType.SALE, "37785316000146", "11111AAAAA22222BBB", null);

        StockHubException exception = Assertions.assertThrows(StockHubException.class, () -> invoiceEntryService.processInvoiceEntry(invoiceEntryRequestDTO));
        Assertions.assertEquals(ExceptionType.INVOICE_ALREADY_SUBMITTED, exception.getExceptionType());
    }

    private InvoiceEntryRequestDTO createInvoiceEntryRequestDTO(String invoiceNumber, String invoiceSeries,
                                                                InvoiceOperationType operation, String dealerCNPJ,
                                                                String chassisNumber, String dealerToTransfer) {
        InvoiceEntryRequestDTO dto = InvoiceEntryRequestDTO.builder()
                .dealerCNPJ(dealerCNPJ)
                .invoiceNumber(invoiceNumber)
                .invoiceSeries(invoiceSeries)
                .emissionDate(LocalDateTime.now())
                .invoiceValue(new BigDecimal(100000))
                .currencyType(CurrencyType.BRL)
                .operationType(operation)

                .products(createInvoiceEntryStockRequestDTO(chassisNumber))
                .build();

        if (operation == InvoiceOperationType.SALE) {
            dto.setCustomerDocumentType(DocumentType.CPF);
            dto.setCustomerLegalNumber("89755885056");
            dto.setCustomerName("Jão Test");
            dto.setCustomerCountry(76L);
            dto.setCustomerState(27L);
            dto.setCustomerCity(2700201L);
            dto.setCustomerAddress("Rua ABC");
            dto.setCustomerComplement("AP 999");

        } else if (operation == InvoiceOperationType.TRANSFER)
            dto.setDealerToTransfer(dealerToTransfer);

        return dto;
    }

    private List<InvoiceEntryStockRequestDTO> createInvoiceEntryStockRequestDTO(String chassisNumber) {
        return List.of(InvoiceEntryStockRequestDTO.builder()
                .chassisNumber(chassisNumber)
                .model("MD29XY0")
                .commercialSeries("SER0000")
                .itemCode("ITCD00000")
                .build());
    }

}