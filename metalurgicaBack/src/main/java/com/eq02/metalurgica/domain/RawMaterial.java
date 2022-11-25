package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RawMaterial.
 */
@Entity
@Table(name = "raw_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(mappedBy = "rawMaterial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rawMaterial", "product" }, allowSetters = true)
    private Set<MadeOf> madeoves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawMaterial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RawMaterial name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return this.stock;
    }

    public RawMaterial stock(Integer stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<MadeOf> getMadeoves() {
        return this.madeoves;
    }

    public void setMadeoves(Set<MadeOf> madeoves) {
        if (this.madeoves != null) {
            this.madeoves.forEach(i -> i.setRawMaterial(null));
        }
        if (madeoves != null) {
            madeoves.forEach(i -> i.setRawMaterial(this));
        }
        this.madeoves = madeoves;
    }

    public RawMaterial madeoves(Set<MadeOf> madeoves) {
        this.setMadeoves(madeoves);
        return this;
    }

    public RawMaterial addMadeOf(MadeOf madeOf) {
        this.madeoves.add(madeOf);
        madeOf.setRawMaterial(this);
        return this;
    }

    public RawMaterial removeMadeOf(MadeOf madeOf) {
        this.madeoves.remove(madeOf);
        madeOf.setRawMaterial(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterial)) {
            return false;
        }
        return id != null && id.equals(((RawMaterial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
