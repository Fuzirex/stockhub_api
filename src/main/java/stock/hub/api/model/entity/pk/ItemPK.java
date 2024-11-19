package stock.hub.api.model.entity.pk;

import jakarta.persistence.*;
import lombok.*;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Stock;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "NUMBER", referencedColumnName = "NUMBER"),
            @JoinColumn(name = "SERIES", referencedColumnName = "SERIES"),
            @JoinColumn(name = "OPERATION_TYPE", referencedColumnName = "OPERATION_TYPE"),
            @JoinColumn(name = "DEALER", referencedColumnName = "DEALER")
    })
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHASSIS_NUMBER", referencedColumnName = "CHASSIS_NUMBER")
    private Stock stock;

}
