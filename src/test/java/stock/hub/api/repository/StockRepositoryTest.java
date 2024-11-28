package stock.hub.api.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.ProductType;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.model.type.StockStatusType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class StockRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    StockRepository stockRepository;

    @Test
    @DisplayName("Should not find Stock successfully from DB when stock does not exists")
    void shouldNotFindStock() {
        Optional<Stock> stock = stockRepository.findById("CTEST123");

        assertThat(stock.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should find Stock successfully from DB")
    void shouldFindStock() {
        createStock();

        Optional<Stock> stock = stockRepository.findById("CTEST123");

        assertThat(stock.isPresent()).isTrue();
    }

    private Stock createStock() {
        Stock stock = Stock.builder()
                .chassisNumber("CTEST123")
                .model("M123")
                .commercialSeries("CS123")
                .itemCode("IC123")
                .status(StockStatusType.AVAILABLE)
                .dealer(createDealer())
                .type(createProductType())
                .build();
        entityManager.persist(stock);
        return stock;
    }

    private ProductType createProductType() {
        ProductType entity = ProductType.builder()
                .type("CO")
                .translationPT("Colheitadeira")
                .translationEN("Combine")
                .translationES("Cosechadora")
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