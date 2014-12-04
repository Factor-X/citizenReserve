package eu.factorx.citizens.util;

public enum BusinessErrorType {

    EMAIL_ALREADY_USED("email_already_used"),//this email is already exist, please choose another one
    CONVERSION_DTO_ERROR("conversion_dto_error"), // fatal error during data transmission
    WRONG_CREDENTIALS("wrong_credentials"), // worng email and / or password
    EMAIL_DOESNT_EXIT("email_doesnt_exit"), //this email doesn't exist => used for 'forgot password'
    WRONG_OLD_PASSWORD("wrong_old_password"), //The old password is wrong
    TOO_MANY_ATTEMPT("too_many_attempt"), //too many attempts, please wait 30 minutes
    EMAIL_EMPTY_PARAMETER("email_empty_parameter"), //one email parameter is empty : {0}
    EMAIL_FATAL_ERROR("email_fatal_error"), //email cannot be send for a unknown error
    NOT_CONNECTED("not_connected"),  // you are not connected => wrong authentication
    WRONG_RIGHT("wrong_right");

    private String messageReference;

    BusinessErrorType(String messageReference) {
        this.messageReference = messageReference;
    }

    public String getMessageReference() {
        return messageReference;
    }
}
