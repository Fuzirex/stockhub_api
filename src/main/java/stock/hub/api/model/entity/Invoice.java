package stock.hub.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import stock.hub.api.model.entity.auditable.Auditable;
import stock.hub.api.model.entity.pk.InvoicePK;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "INVOICE")
public class Invoice extends Auditable implements Serializable {

    @EmbeddedId
    private InvoicePK pk;

    @Column(name = "EMISSION_DATE", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime emissionDate;

    @Column(name = "CURRENCY", length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "VALUE", precision = 14, scale = 2)
    private BigDecimal value;

    @JoinColumn(name = "DEALER_TO_TRANSFER", referencedColumnName = "CNPJ")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dealer dealerToTransfer;

    @Column(name = "CUSTOMER_DOCUMENT_TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private DocumentType customerDocumentType;

    @Column(name = "CUSTOMER_LEGAL_NUMBER", length = 20)
    private String customerLegalNumber;

    @Column(name = "CUSTOMER_NAME", length = 100)
    private String customerName;

    @Column(name = "CUSTOMER_COUNTRY", length = 100)
    private String customerCountry;

    @Column(name = "CUSTOMER_COUNTRY_ID")
    private Long customerCountryID;

    @Column(name = "CUSTOMER_STATE", length = 100)
    private String customerState;

    @Column(name = "CUSTOMER_STATE_ID")
    private Long customerStateID;

    @Column(name = "CUSTOMER_CITY", length = 100)
    private String customerCity;

    @Column(name = "CUSTOMER_CITY_ID")
    private Long customerCityID;

    @Column(name = "CUSTOMER_ADDRESS", length = 255)
    private String customerAddress;

    @Column(name = "CUSTOMER_ADDRESS_COMPLEMENT", length = 255)
    private String customerAddressComplement;

    @OneToMany(mappedBy = "pk.invoice", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> items;

    public Invoice(InvoicePK pk) {
        this.pk = pk;
    }
}
