package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.Date;

public class SheddingRiskDTO extends DTO {

	private Long id;

	private Date riskDate;

	private Date alertSendingDate;

	public SheddingRiskDTO() {
	}

	public SheddingRiskDTO(Long id, Date riskDate, Date alertSendingDate) {
		this.id = id;
		this.riskDate = riskDate;
		this.alertSendingDate = alertSendingDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRiskDate() {
		return riskDate;
	}

	public void setRiskDate(Date riskDate) {
		this.riskDate = riskDate;
	}

	public Date getAlertSendingDate() {
		return alertSendingDate;
	}

	public void setAlertSendingDate(Date alertSendingDate) {
		this.alertSendingDate = alertSendingDate;
	}
}
