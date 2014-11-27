package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.HashMap;
import java.util.Map;

public class ReductionDTO extends DTO {

	private Map<String, Double> powerReductionByPeriod = new HashMap<>();

	public ReductionDTO() {
	}

	public Map<String, Double> getPowerReductionByPeriod() {
		return powerReductionByPeriod;
	}

	public void setPowerReductionByPeriod(Map<String, Double> powerReductionByPeriod) {
		this.powerReductionByPeriod = powerReductionByPeriod;
	}
}
