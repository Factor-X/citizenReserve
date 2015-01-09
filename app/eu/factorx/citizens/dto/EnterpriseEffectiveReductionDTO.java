package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

public class EnterpriseEffectiveReductionDTO extends DTO {

	private double meanPower;
	private double kwh;

	public EnterpriseEffectiveReductionDTO() {
	}

	public void setMeanPower(double meanPower) {
		this.meanPower = meanPower;
	}

	public double getMeanPower() {
		return meanPower;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public double getKwh() {
		return kwh;
	}
}
