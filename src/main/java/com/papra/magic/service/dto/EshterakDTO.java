package com.papra.magic.service.dto;

import com.papra.magic.domain.enumeration.UntilType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papra.magic.domain.Eshterak} entity.
 */
public class EshterakDTO implements Serializable {

    private Long id;

    private String text;

    private Long amount;

    private Long until;

    private UntilType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUntil() {
        return until;
    }

    public void setUntil(Long until) {
        this.until = until;
    }

    public UntilType getType() {
        return type;
    }

    public void setType(UntilType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EshterakDTO)) {
            return false;
        }

        EshterakDTO eshterakDTO = (EshterakDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eshterakDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EshterakDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", amount=" + getAmount() +
            ", until=" + getUntil() +
            ", type='" + getType() + "'" +
            "}";
    }
}
