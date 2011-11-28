package com.all.shared.model.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import javax.validation.Constraint;
import javax.validation.ConstraintPayload;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ReportAsSingleViolation;

@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = SameValue.SameValueValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface SameValue {
	String message() default "{validation.sameValue}";
	String first() default "";
	String second() default "";
	Class<? extends ConstraintPayload>[] payload() default { };
	
	Class<?>[] groups() default {};

	class SameValueValidator implements ConstraintValidator<SameValue, Object> {
		private String first;
		private String second ;

		public void initialize(SameValue constraintAnnotation) {
			first = constraintAnnotation.first();
			second = constraintAnnotation.second();
		}

		public boolean isValid(Object generatingObject, ConstraintValidatorContext context) {
			try {
				//TODO: We should re-write the way we get the parameters
				Field declaredField1 = generatingObject.getClass().getDeclaredField(first);
				Field declaredField2 = generatingObject.getClass().getDeclaredField(second);
				declaredField1.setAccessible(true);
				declaredField2.setAccessible(true);
				Object value1 = declaredField1.get(generatingObject);
				Object value2 = declaredField2.get(generatingObject);
				return value1.equals(value2);
			} catch (Exception e) {
				return false;
			}
		}
	}
}
