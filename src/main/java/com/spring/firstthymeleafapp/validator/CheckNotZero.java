package com.spring.firstthymeleafapp.validator;
import lombok.extern.slf4j.Slf4j;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CheckNotZero  implements ConstraintValidator<NotZero, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return value !=null && value !=0;
    }

    @Override
    public void initialize(NotZero constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
