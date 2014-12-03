package eu.factorx.citizens.util;

public enum BusinessErrorType {

    EMAIL_ALREADY_USED,//this email is already exist, please choose another one
    INVALID_PASSWORD,//your password must be composed of 6-16 alphanuearic caracters
    INVALID_EMAIL, //this is not a valid email
    CONVERSION_DTO_ERROR, // fatal error during data transmission
    WRONG_CREDENTIALS, // worng email and / or password
    EMAIL_DOESNT_EXIT, //this email doesn't exist => used for 'forgot password'
    WRONG_OLD_PASSWORD, //The old password is wrong
    TOO_MANY_ATTEMPT, //too many attempts, please wait 30 minutes
    EMAIL_EMPTY_PARAMETER, //one email parameter is empty : {0}
    EMAIL_FATAL_ERROR, //email cannot be send for a unknown error
    NOT_CONNECTED // you are not connected => wrong authentication
}
