package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MadeOf.
 */
@Entity
@Table(name = "made_of")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MadeOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount_material")
    private Double amountMaterial;

    @ManyToOne
    @JsonIgnoreProperties(value = { "madeoves" }, allowSetters = true)
    private RawMaterial rawMaterial;

    @ManyToOne
    @JsonIgnoreProperties(value = { "madeoves", "roows" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MadeOf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountMaterial() {
        return this.amountMaterial;
    }

    public MadeOf amountMaterial(Double amountMaterial) {
        this.setAmountMaterial(amountMaterial);
        return this;
    }

    public void setAmountMaterial(Double amountMaterial) {
        this.amountMaterial = amountMaterial;
    }

    public RawMaterial getRawMaterial() {
        return this.rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public MadeOf rawMaterial(RawMaterial rawMaterial) {
        this.setRawMaterial(rawMaterial);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MadeOf product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MadeOf)) {
            return false;
        }
        return id != null && id.equals(((MadeOf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MadeOf{" +
            "id=" + getId() +
            ", amountMaterial=" + getAmountMaterial() +
            "}";
    }
}
