package com.papra.magic.domain;

import com.papra.magic.domain.enumeration.UntilType;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Eshterak.
 */
@Entity
@Table(name = "eshterak")
public class Eshterak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "until")
    private Long until;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UntilType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Eshterak id(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public Eshterak text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Eshterak amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUntil() {
        return this.until;
    }

    public Eshterak until(Long until) {
        this.until = until;
        return this;
    }

    public void setUntil(Long until) {
        this.until = until;
    }

    public UntilType getType() {
        return this.type;
    }

    public Eshterak type(UntilType type) {
        this.type = type;
        return this;
    }

    public void setType(UntilType type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eshterak)) {
            return false;
        }
        return id != null && id.equals(((Eshterak) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eshterak{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", amount=" + getAmount() +
            ", until=" + getUntil() +
            ", type='" + getType() + "'" +
            "}";
    }
}
