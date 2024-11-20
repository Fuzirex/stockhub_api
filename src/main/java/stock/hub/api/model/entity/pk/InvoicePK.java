package stock.hub.api.model.entity.pk;

import jakarta.persistence.*;
import lombok.*;
import stock.hub.api.model.converter.InvoiceOperationTypeConverter;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.type.InvoiceOperationType;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InvoicePK implements Serializable {

    @Column(name = "NUMBER", length = 15)
    private String number;

    @Column(name = "SERIES", length = 15)
    private String series;

    @Column(name = "OPERATION_TYPE")
    @Convert(converter = InvoiceOperationTypeConverter.class)
    private InvoiceOperationType operationType;

    @JoinColumn(name = "DEALER", referencedColumnName = "CNPJ")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dealer dealer;

}
