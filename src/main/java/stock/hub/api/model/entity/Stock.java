package stock.hub.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import stock.hub.api.model.converter.StockStatusTypeConverter;
import stock.hub.api.model.entity.auditable.Auditable;
import stock.hub.api.model.type.StockStatusType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STOCK")
public class Stock extends Auditable {

    @Id
    @Column(name = "CHASSIS_NUMBER", length = 20, nullable = false)
    private String chassisNumber;

    @Column(name = "MODEL", length = 20, nullable = false)
    private String model;

    @Column(name = "COMMERCIAL_SERIES", length = 20, nullable = false)
    private String commercialSeries;

    @Column(name = "ITEM_CODE", length = 20, nullable = false)
    private String itemCode;

    @JoinColumn(name = "DEALER", referencedColumnName = "CNPJ")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dealer dealer;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = StockStatusTypeConverter.class)
    private StockStatusType status;

    @JoinColumn(name = "TYPE", referencedColumnName = "TYPE")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType type;

}
