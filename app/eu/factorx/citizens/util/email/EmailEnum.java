package eu.factorx.citizens.util.email;

public enum EmailEnum {

	FORGOT_PASSWORD("email.forgotPassword.subject", "email.forgotPassword.content",
		new EmailParams(0, "firstName"),
		new EmailParams(1, "lastName"),
		new EmailParams(2, "newPassword")),
	ENTERPRISE_SUMMARY("email.registration.subject", "email.registration.content",
		new EmailParams(0, "firstName"),
		new EmailParams(1, "lastName"),
		new EmailParams(2, "meanPower"),
		new EmailParams(3, "actionTable"),
		new EmailParams(4, "personal_access_url")),
	SUMMARY("email.registration.subject", "email.registration.content",
		new EmailParams(0, "firstName"),
		new EmailParams(1, "lastName"),
		new EmailParams(2, "reductionSum"),
		new EmailParams(3, "actionTable"),
		new EmailParams(4, "personal_access_url"));


	private String subjectKey;
	private String contentKey;
	private EmailParams[] expectedParams;

	private EmailEnum(String subjectKey, String contentKey, EmailParams... expectedParams) {
		this.subjectKey = subjectKey;
		this.contentKey = contentKey;
		this.expectedParams = expectedParams;
	}

	public String getSubjectKey() {
		return subjectKey;
	}

	public String getContentKey() {
		return contentKey;
	}

	public EmailParams[] getExpectedParams() {
		return expectedParams;
	}
}
