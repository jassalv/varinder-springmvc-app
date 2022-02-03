package com.spring.firstthymeleafapp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckNotZero.class)
public @interface NotZero {
    String message() default "{ dateformat.message.key }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
