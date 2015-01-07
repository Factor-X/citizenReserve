package eu.factorx.citizens.model.survey;

import eu.factorx.citizens.model.technical.AbstractEntity;
import eu.factorx.citizens.model.type.QuestionCode;
import org.joda.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "action_answers")
public class ActionAnswer extends AbstractEntity {

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Survey survey;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private QuestionCode questionCode;

	// only for user-defined actions
	@Basic(optional = true)
	private String title;

	private Integer powerReduction;

	private LocalTime startTime;

	private Long duration;

	private String comment;

	public ActionAnswer(Survey survey, QuestionCode questionCode, Integer powerReduction, LocalTime startTime, Long duration, String comment) {
		this(survey, questionCode, null, powerReduction, startTime, duration, comment);
	}

	public ActionAnswer(Survey survey, QuestionCode questionCode, String title, Integer powerReduction, LocalTime startTime, Long duration, String comment) {
		this.survey = survey;
		this.questionCode = questionCode;
		this.title = title;
		this.powerReduction = powerReduction;
		this.startTime = startTime;
		this.duration = duration;
		this.comment = comment;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public QuestionCode getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(QuestionCode questionCode) {
		this.questionCode = questionCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPowerReduction() {
		return powerReduction;
	}

	public void setPowerReduction(Integer powerReduction) {
		this.powerReduction = powerReduction;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
