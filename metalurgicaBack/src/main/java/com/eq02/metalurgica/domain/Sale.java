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
 * A Sale.
 */
@Entity
@Table(name = "sale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sale_code")
    private Integer saleCode;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "sale")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sale", "product" }, allowSetters = true)
    private Set<Roow> roows = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "emloyee", "sales" }, allowSetters = true)
    private SalesPerson salesPerson;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sales" }, allowSetters = true)
    private Record record;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSaleCode() {
        return this.saleCode;
    }

    public Sale saleCode(Integer saleCode) {
        this.setSaleCode(saleCode);
        return this;
    }

    public void setSaleCode(Integer saleCode) {
        this.saleCode = saleCode;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Sale date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotal() {
        return this.total;
    }

    public Sale total(Double total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<Roow> getRoows() {
        return this.roows;
    }

    public void setRoows(Set<Roow> roows) {
        if (this.roows != null) {
            this.roows.forEach(i -> i.setSale(null));
        }
        if (roows != null) {
            roows.forEach(i -> i.setSale(this));
        }
        this.roows = roows;
    }

    public Sale roows(Set<Roow> roows) {
        this.setRoows(roows);
        return this;
    }

    public Sale addRoow(Roow roow) {
        this.roows.add(roow);
        roow.setSale(this);
        return this;
    }

    public Sale removeRoow(Roow roow) {
        this.roows.remove(roow);
        roow.setSale(null);
        return this;
    }

    public SalesPerson getSalesPerson() {
        return this.salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public Sale salesPerson(SalesPerson salesPerson) {
        this.setSalesPerson(salesPerson);
        return this;
    }

    public Record getRecord() {
        return this.record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Sale record(Record record) {
        this.setRecord(record);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", saleCode=" + getSaleCode() +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
