package stock.hub.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.pk.InvoicePK;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, InvoicePK> {
}
