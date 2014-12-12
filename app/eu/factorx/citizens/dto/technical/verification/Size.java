package eu.factorx.citizens.dto.technical.verification;

import eu.factorx.citizens.util.BusinessErrorType;

/**
 * Created by florian on 7/12/14.
 */
public @interface Size {

    BusinessErrorType message() default BusinessErrorType.VALIDATION_SIZE;

    int min() default 1;

    int max() default 255;
}
