package stock.hub.api.model.entity.pk;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import stock.hub.api.model.converter.InvoiceOperationTypeConverter;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.type.InvoiceOperationType;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        InvoicePK itemToCompare = (InvoicePK) obj;
        return StringUtils.equals(this.number, itemToCompare.number)
                && StringUtils.equals(this.series, itemToCompare.series)
                && this.operationType == itemToCompare.operationType
                && StringUtils.equals(this.dealer.getCnpj(), itemToCompare.dealer.getCnpj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, series, operationType, dealer);
    }
}
