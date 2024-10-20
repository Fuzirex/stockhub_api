package stock.hub.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.entity.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, String> {
}
