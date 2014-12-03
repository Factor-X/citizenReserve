package eu.factorx.citizens.util.email;

/**
 * Created by florian on 3/12/14.
 */
public enum EmailEnum {

    FORGOT_PASSWORD("email.forgotPassword.subject", "email.forgotPassword.content",
            new EmailParams(0, "firstName"),
            new EmailParams(1, "lastName"),
            new EmailParams(2, "newPassword"));


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
