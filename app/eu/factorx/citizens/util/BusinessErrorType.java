package eu.factorx.citizens.util;

public enum BusinessErrorType {

    EMAIL_ALREADY_USED,//this email is already exist, please choose another one
    INVALID_PASSWORD,//your password must be composed of 6-16 alphanuearic caracters
    INVALID_EMAIL, //this is not a valid email
    CONVERSION_DTO_ERROR, // fatal error during data transmission
    WRONG_CREDENTIALS, // worng email and / or password
    EMAIL_DOESNT_EXIT, //this email doesn't exist => used for 'forgot password'
    NOT_CONNECTED // you are not connected => wrong authentication
}
