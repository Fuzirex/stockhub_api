package stock.hub.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {
}
