package stock.hub.api.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import stock.hub.api.model.entity.ProductType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductTypeRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Test
    @DisplayName("Should not find Product Type successfully from DB when it does not exists")
    void shouldNotFindProductType() {
        Optional<ProductType> entity = productTypeRepository.findById("TA");

        assertThat(entity.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should find Product Type successfully from DB")
    void shouldFindProductType() {
        createProductType();

        Optional<ProductType> entity = productTypeRepository.findById("TA");

        assertThat(entity.isPresent()).isTrue();
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

}