package com.social.server.validator.birthday;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthdayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthdayValid {
    String message() default "Invalid birthday";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
