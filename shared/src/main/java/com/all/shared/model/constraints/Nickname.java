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
@NotEmpty
@Size(max = 25, min = 4)
@Pattern(regexp = "[A-Za-z0-9._-]*")
@ReportAsSingleViolation
@Constraint(validatedBy = Nickname.NameValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Nickname {
	String message() default "{validation.nickname.size}";

	Class<?>[] groups() default {};

	Class<? extends ConstraintPayload>[] payload() default {};

	class NameValidator implements ConstraintValidator<Nickname, String> {
		public void initialize(Nickname constraintAnnotation) {
		}

		public boolean isValid(String value, ConstraintValidatorContext context) {
			return true;
		}
	}
}
