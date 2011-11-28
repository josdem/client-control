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
@Size(max = 40, min = 0)
@Pattern(regexp = "[A-Z\u00C1\u00C9\u00CD\u00D3\u00DA\u00D1]*[a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00F1 -]*")
@ReportAsSingleViolation
@Constraint(validatedBy = Name.NameValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Name {
	String message() default "{validation.name.invalid}";

	Class<?>[] groups() default {};

	Class<? extends ConstraintPayload>[] payload() default {};

	class NameValidator implements ConstraintValidator<Name, String> {
		public void initialize(Name constraintAnnotation) {
		}

		public boolean isValid(String value, ConstraintValidatorContext context) {
			return true;
		}
	}
}
