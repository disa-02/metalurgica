package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesPerson.
 */
@Entity
@Table(name = "sales_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee emloyee;

    @OneToMany(mappedBy = "salesPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roows", "salesPerson", "record" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesPerson id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmloyee() {
        return this.emloyee;
    }

    public void setEmloyee(Employee employee) {
        this.emloyee = employee;
    }

    public SalesPerson emloyee(Employee employee) {
        this.setEmloyee(employee);
        return this;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setSalesPerson(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setSalesPerson(this));
        }
        this.sales = sales;
    }

    public SalesPerson sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public SalesPerson addSale(Sale sale) {
        this.sales.add(sale);
        sale.setSalesPerson(this);
        return this;
    }

    public SalesPerson removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setSalesPerson(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesPerson)) {
            return false;
        }
        return id != null && id.equals(((SalesPerson) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesPerson{" +
            "id=" + getId() +
            "}";
    }
}
