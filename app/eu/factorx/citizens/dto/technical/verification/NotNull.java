package eu.factorx.citizens.dto.technical.verification;

import eu.factorx.citizens.util.BusinessErrorType;

/**
 * Created by florian on 7/12/14.
 */
public @interface NotNull {

    BusinessErrorType message() default BusinessErrorType.VALIDATION_NOT_NULL;
}
