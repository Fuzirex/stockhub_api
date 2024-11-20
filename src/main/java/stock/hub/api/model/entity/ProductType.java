package stock.hub.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import stock.hub.api.model.entity.auditable.Auditable;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType implements Serializable {

    @Id
    @Column(name = "TYPE", length = 3, nullable = false)
    private String type;

    @Column(name = "TRANSLATION_PT", length = 50, nullable = false)
    private String translationPT;

    @Column(name = "TRANSLATION_EN", length = 50, nullable = false)
    private String translationEN;

    @Column(name = "TRANSLATION_ES", length = 50, nullable = false)
    private String translationES;

}
