package com.eq02.metalurgica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo")
    private Integer tipo;

    @OneToMany(mappedBy = "notification")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notification", "operator" }, allowSetters = true)
    private Set<Subscribe> subscribes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Notification descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return this.tipo;
    }

    public Notification tipo(Integer tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Set<Subscribe> getSubscribes() {
        return this.subscribes;
    }

    public void setSubscribes(Set<Subscribe> subscribes) {
        if (this.subscribes != null) {
            this.subscribes.forEach(i -> i.setNotification(null));
        }
        if (subscribes != null) {
            subscribes.forEach(i -> i.setNotification(this));
        }
        this.subscribes = subscribes;
    }

    public Notification subscribes(Set<Subscribe> subscribes) {
        this.setSubscribes(subscribes);
        return this;
    }

    public Notification addSubscribe(Subscribe subscribe) {
        this.subscribes.add(subscribe);
        subscribe.setNotification(this);
        return this;
    }

    public Notification removeSubscribe(Subscribe subscribe) {
        this.subscribes.remove(subscribe);
        subscribe.setNotification(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipo=" + getTipo() +
            "}";
    }
}
