package stock.hub.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.entity.Item;
import stock.hub.api.model.entity.pk.ItemPK;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemPK> {
}
