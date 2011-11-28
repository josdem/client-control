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

@Documented
@NotEmpty
@Pattern(regexp = Email.patternString)
@ReportAsSingleViolation
@Constraint(validatedBy = Email.EmailValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Email {
	String message() default "{validation.email.invalid}";

	static final String patternString = "^(([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5}){1,25})+([;.](([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5}){1,25})+)*$";

	Class<?>[] groups() default {};

	Class<? extends ConstraintPayload>[] payload() default {};

	class EmailValidator implements ConstraintValidator<Email, String> {
		public void initialize(Email constraintAnnotation) {
		}

		public boolean isValid(String value, ConstraintValidatorContext context) {
			return true;
		}
	}
}
