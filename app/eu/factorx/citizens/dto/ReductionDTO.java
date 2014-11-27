package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

public class ReductionDTO extends DTO {

	private Double firstPeriodPowerReduction;

	private Double secondPeriodPowerReduction;

	private Double thirdPeriodPowerReduction;

	private Double averagePowerReduction;

	private Double energyReduction;

	public ReductionDTO() {
	}

	public Double getFirstPeriodPowerReduction() {
		return firstPeriodPowerReduction;
	}

	public void setFirstPeriodPowerReduction(Double firstPeriodPowerReduction) {
		this.firstPeriodPowerReduction = firstPeriodPowerReduction;
	}

	public Double getSecondPeriodPowerReduction() {
		return secondPeriodPowerReduction;
	}

	public void setSecondPeriodPowerReduction(Double secondPeriodPowerReduction) {
		this.secondPeriodPowerReduction = secondPeriodPowerReduction;
	}

	public Double getThirdPeriodPowerReduction() {
		return thirdPeriodPowerReduction;
	}

	public void setThirdPeriodPowerReduction(Double thirdPeriodPowerReduction) {
		this.thirdPeriodPowerReduction = thirdPeriodPowerReduction;
	}

	public Double getAveragePowerReduction() {
		return averagePowerReduction;
	}

	public void setAveragePowerReduction(Double averagePowerReduction) {
		this.averagePowerReduction = averagePowerReduction;
	}

	public Double getEnergyReduction() {
		return energyReduction;
	}

	public void setEnergyReduction(Double energyReduction) {
		this.energyReduction = energyReduction;
	}
}
