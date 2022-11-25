package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

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

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rawMaterial", "product" }, allowSetters = true)
    private Set<MadeOf> madeoves = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sale", "product" }, allowSetters = true)
    private Set<Roow> roows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Product stock(Integer stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getBuyPrice() {
        return this.buyPrice;
    }

    public Product buyPrice(Double buyPrice) {
        this.setBuyPrice(buyPrice);
        return this;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return this.sellPrice;
    }

    public Product sellPrice(Double sellPrice) {
        this.setSellPrice(sellPrice);
        return this;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Set<MadeOf> getMadeoves() {
        return this.madeoves;
    }

    public void setMadeoves(Set<MadeOf> madeoves) {
        if (this.madeoves != null) {
            this.madeoves.forEach(i -> i.setProduct(null));
        }
        if (madeoves != null) {
            madeoves.forEach(i -> i.setProduct(this));
        }
        this.madeoves = madeoves;
    }

    public Product madeoves(Set<MadeOf> madeoves) {
        this.setMadeoves(madeoves);
        return this;
    }

    public Product addMadeOf(MadeOf madeOf) {
        this.madeoves.add(madeOf);
        madeOf.setProduct(this);
        return this;
    }

    public Product removeMadeOf(MadeOf madeOf) {
        this.madeoves.remove(madeOf);
        madeOf.setProduct(null);
        return this;
    }

    public Set<Roow> getRoows() {
        return this.roows;
    }

    public void setRoows(Set<Roow> roows) {
        if (this.roows != null) {
            this.roows.forEach(i -> i.setProduct(null));
        }
        if (roows != null) {
            roows.forEach(i -> i.setProduct(this));
        }
        this.roows = roows;
    }

    public Product roows(Set<Roow> roows) {
        this.setRoows(roows);
        return this;
    }

    public Product addRoow(Roow roow) {
        this.roows.add(roow);
        roow.setProduct(this);
        return this;
    }

    public Product removeRoow(Roow roow) {
        this.roows.remove(roow);
        roow.setProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stock=" + getStock() +
            ", buyPrice=" + getBuyPrice() +
            ", sellPrice=" + getSellPrice() +
            "}";
    }
}
