package eu.factorx.citizens.model.shedding;

import eu.factorx.citizens.model.account.Account;
import eu.factorx.citizens.model.technical.AbstractEntity;
import org.joda.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shedding_risks")
public class SheddingRisk extends AbstractEntity {

	private LocalDate riskDate;

	@OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SheddingRiskAnswer> answers;

	public SheddingRisk(LocalDate riskDate) {
		this.riskDate = riskDate;
		this.answers = new ArrayList<>();
	}

	public LocalDate getRiskDate() {
		return riskDate;
	}

	public void setRiskDate(LocalDate riskDate) {
		this.riskDate = riskDate;
	}

	public List<SheddingRiskAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<SheddingRiskAnswer> answers) {
		this.answers = answers;
	}

	public void addAnswer(Account account, boolean answer) {
		this.answers.add(new SheddingRiskAnswer(this, account, answer));
	}
}
