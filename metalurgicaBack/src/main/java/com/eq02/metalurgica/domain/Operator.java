package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Operator.
 */
@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee emloyee;

    @OneToMany(mappedBy = "operator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notification", "operator" }, allowSetters = true)
    private Set<Subscribe> subscribes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Operator id(Long id) {
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

    public Operator emloyee(Employee employee) {
        this.setEmloyee(employee);
        return this;
    }

    public Set<Subscribe> getSubscribes() {
        return this.subscribes;
    }

    public void setSubscribes(Set<Subscribe> subscribes) {
        if (this.subscribes != null) {
            this.subscribes.forEach(i -> i.setOperator(null));
        }
        if (subscribes != null) {
            subscribes.forEach(i -> i.setOperator(this));
        }
        this.subscribes = subscribes;
    }

    public Operator subscribes(Set<Subscribe> subscribes) {
        this.setSubscribes(subscribes);
        return this;
    }

    public Operator addSubscribe(Subscribe subscribe) {
        this.subscribes.add(subscribe);
        subscribe.setOperator(this);
        return this;
    }

    public Operator removeSubscribe(Subscribe subscribe) {
        this.subscribes.remove(subscribe);
        subscribe.setOperator(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operator)) {
            return false;
        }
        return id != null && id.equals(((Operator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            "}";
    }
}
