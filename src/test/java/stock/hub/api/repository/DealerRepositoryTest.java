package stock.hub.api.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import stock.hub.api.model.entity.Dealer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DealerRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DealerRepository dealerRepository;

    @Test
    @DisplayName("Should not find Dealer successfully from DB when it does not exists")
    void shouldNotFindDealer() {
        Optional<Dealer> entity = dealerRepository.findById("98987343000196");

        assertThat(entity.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should find Dealer successfully from DB")
    void shouldFindDealer() {
        createDealer();

        Optional<Dealer> entity = dealerRepository.findById("98987343000196");

        assertThat(entity.isPresent()).isTrue();
    }

    private Dealer createDealer() {
        Dealer entity = Dealer.builder()
                .cnpj("98987343000196")
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