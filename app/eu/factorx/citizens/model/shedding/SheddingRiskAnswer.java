package eu.factorx.citizens.model.shedding;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.technical.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "shedding_risks_answers")
public class SheddingRiskAnswer extends AbstractEntity {

	public static final String PROPERTY_UUID = "uuid";
	@ManyToOne
	private SheddingRisk risk;

	@ManyToOne
	private Account account;

	private Boolean answer;

	@Column(unique = true)
	private String uuid;

	public SheddingRiskAnswer(SheddingRisk risk, Account account, Boolean answer) {
		this.risk = risk;
		this.account = account;
		this.answer = answer;
		this.uuid = UUID.randomUUID().toString();
	}

	public SheddingRisk getRisk() {
		return risk;
	}

	public void setRisk(SheddingRisk risk) {
		this.risk = risk;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Boolean getAnswer() {
		return answer;
	}

	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
