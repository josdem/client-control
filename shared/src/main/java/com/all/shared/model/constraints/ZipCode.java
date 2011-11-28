package com.all.shared.model.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintPayload;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Documented
@Size(max = 15, min = 0)
@Pattern(regexp = "[a-zA-Z0-9-_\\.@]*")
@ReportAsSingleViolation
@Constraint(validatedBy = ZipCode.ZipCodeValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ZipCode {

	String message() default "{constraint.zipcode.invalid}";

	Class<?>[] groups() default {};

	Class<? extends ConstraintPayload>[] payload() default {};

	class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {

		@Override
		public void initialize(ZipCode constraintAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
			return true;
		}

	}

}
