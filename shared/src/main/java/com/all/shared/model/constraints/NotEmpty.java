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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Documented
@NotNull
@Size(min = 1)
@ReportAsSingleViolation
@Constraint(validatedBy = NotEmpty.NotEmptyValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface NotEmpty {
	String message() default "{validation.notEmpty}";

	Class<?>[] groups() default { };
	Class<? extends ConstraintPayload>[] payload() default { };
	class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
		public void initialize(NotEmpty constraintAnnotation) {
		}

		public boolean isValid(String value, ConstraintValidatorContext context) {
			return true;
		}
	}
}
