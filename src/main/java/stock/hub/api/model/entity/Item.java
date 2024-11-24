package stock.hub.api.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import stock.hub.api.model.entity.auditable.Auditable;
import stock.hub.api.model.entity.pk.ItemPK;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ITEM")
public class Item extends Auditable implements Serializable {

    @EmbeddedId
    private ItemPK pk;

    public Item(Invoice invoice, Stock stock) {
        this.pk = new ItemPK(invoice, stock);
    }
}
