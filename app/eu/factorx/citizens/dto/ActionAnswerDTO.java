package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

public class ActionAnswerDTO extends DTO {

	private String questionKey;

	private String title;
	private Integer power;
	private String begin;
	private String duration;
	private String description;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ActionAnswerDTO{" +
			"questionKey='" + questionKey + '\'' +
			", title='" + title + '\'' +
			", power=" + power +
			", begin='" + begin + '\'' +
			", duration='" + duration + '\'' +
			", description='" + description + '\'' +
			'}';
	}
}
