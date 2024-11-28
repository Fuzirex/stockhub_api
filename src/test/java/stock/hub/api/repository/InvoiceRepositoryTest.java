package stock.hub.api.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import stock.hub.api.model.entity.*;
import stock.hub.api.model.entity.pk.InvoicePK;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.model.type.StockStatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class InvoiceRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    @DisplayName("Should not find Invoice successfully from DB when it does not exists")
    void shouldNotFindInvoice() {
        Optional<Invoice> entity = invoiceRepository.findById(new InvoicePK("123", "123", InvoiceOperationType.SALE, new Dealer("37785316000146")));

        assertThat(entity.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should find Dealer successfully from DB")
    void shouldFindStock() {
        createInvoice();

        Optional<Invoice> entity = invoiceRepository.findById(new InvoicePK("123", "123", InvoiceOperationType.SALE, new Dealer("37785316000146")));

        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getItems()).isNotEmpty();
    }

    private Invoice createInvoice() {
        Dealer dealer = createDealer();

        Invoice invoice =
                Invoice.builder()
                        .pk(new InvoicePK("123", "123", InvoiceOperationType.SALE, dealer))

                        .emissionDate(LocalDateTime.now())
                        .value(new BigDecimal(100000))
                        .currency(CurrencyType.BRL)

                        .customerDocumentType(DocumentType.CPF)
                        .customerLegalNumber("89755885056")
                        .customerName("Jão Test")
                        .customerCountry("Brasil")
                        .customerCountryID(76L)
                        .customerState("Rio Grande do Sul")
                        .customerStateID(27L)
                        .customerCity("Lajeado")
                        .customerCityID(2700201L)
                        .customerAddress("Rua ABC")
                        .customerAddressComplement("AP 999")

                        .build();

        invoice.setItems(List.of(new Item(invoice, createStock(dealer))));

        entityManager.persist(invoice);
        return invoice;
    }

    private Stock createStock(Dealer dealer) {
        Stock stock = Stock.builder()
                .chassisNumber("CTEST123")
                .model("M123")
                .commercialSeries("CS123")
                .itemCode("IC123")
                .status(StockStatusType.AVAILABLE)
                .dealer(dealer)
                .type(createProductType())
                .build();
        entityManager.persist(stock);
        return stock;
    }

    private ProductType createProductType() {
        ProductType entity = ProductType.builder()
                .type("TA")
                .translationPT("Trator")
                .translationEN("Tractor")
                .translationES("Tractor")
                .build();
        entityManager.persist(entity);
        return entity;
    }

    private Dealer createDealer() {
        Dealer entity = Dealer.builder()
                .cnpj("37785316000146")
                .name("Dealer 1")
                .email("dealer1@example.com")
                .countryId(76L)
                .countryDesc("Brasil")
                .stateId(43L)
                .stateDesc("Rio Grande do Sul")
                .cityId(4311403L)
                .cityDesc("Lajeado")
                .active(true)
                .password("password")
                .build();
        entityManager.persist(entity);
        return entity;
    }

}