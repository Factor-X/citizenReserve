package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.List;

public class EffectiveReductionDTO extends DTO {


	private List<ReductionDTO> reductions;

	public EffectiveReductionDTO() {
	}

	public List<ReductionDTO> getReductions() {
		return reductions;
	}

	public void setReductions(List<ReductionDTO> reductions) {
		this.reductions = reductions;
	}

    @Override
    public String toString() {
        return "EffectiveReductionDTO{" +
                ", reductions=" + reductions +
                '}';
    }
}
