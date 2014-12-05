package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

/**
 * Created by florian on 5/12/14.
 */
public class DoubleDTO extends DTO {

    private Double value;

    public DoubleDTO(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DoubleDTO{" +
                "value=" + value +
                '}';
    }
}
