package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_range")
    private LocalDate dateRange;

    @Column(name = "amount")
    private Double amount;

    @OneToMany(mappedBy = "record")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roows", "salesPerson", "record" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Record id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRange() {
        return this.dateRange;
    }

    public Record dateRange(LocalDate dateRange) {
        this.setDateRange(dateRange);
        return this;
    }

    public void setDateRange(LocalDate dateRange) {
        this.dateRange = dateRange;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Record amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setRecord(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setRecord(this));
        }
        this.sales = sales;
    }

    public Record sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public Record addSale(Sale sale) {
        this.sales.add(sale);
        sale.setRecord(this);
        return this;
    }

    public Record removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setRecord(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Record)) {
            return false;
        }
        return id != null && id.equals(((Record) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Record{" +
            "id=" + getId() +
            ", dateRange='" + getDateRange() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
