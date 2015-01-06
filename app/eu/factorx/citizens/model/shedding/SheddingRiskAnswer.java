package eu.factorx.citizens.model.shedding;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.technical.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shedding_risks_answers")
public class SheddingRiskAnswer extends AbstractEntity {

	@ManyToOne
	private SheddingRisk risk;

	@ManyToOne
	private Account account;

	private Boolean answer;

	public SheddingRiskAnswer(SheddingRisk risk, Account account, Boolean answer) {
		this.risk = risk;
		this.account = account;
		this.answer = answer;
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
}
