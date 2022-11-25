package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Roow.
 */
@Entity
@Table(name = "roow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Roow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount_product")
    private Integer amountProduct;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roows", "salesPerson", "record" }, allowSetters = true)
    private Sale sale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "madeoves", "roows" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Roow id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmountProduct() {
        return this.amountProduct;
    }

    public Roow amountProduct(Integer amountProduct) {
        this.setAmountProduct(amountProduct);
        return this;
    }

    public void setAmountProduct(Integer amountProduct) {
        this.amountProduct = amountProduct;
    }

    public Double getSubTotal() {
        return this.subTotal;
    }

    public Roow subTotal(Double subTotal) {
        this.setSubTotal(subTotal);
        return this;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Sale getSale() {
        return this.sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Roow sale(Sale sale) {
        this.setSale(sale);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Roow product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Roow)) {
            return false;
        }
        return id != null && id.equals(((Roow) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roow{" +
            "id=" + getId() +
            ", amountProduct=" + getAmountProduct() +
            ", subTotal=" + getSubTotal() +
            "}";
    }
}
