package com.all.shared.model.constraints;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;


public class TestSameValue {
	private ValidatorFactory validatorFactory;
	private Validator validator;
	Bean bean = new Bean();

	class Bean {
		@SameValue(first="one", second="two")
		Bean thisValue=this;
//		@SameValue(first="some", second="that")
//		Bean thisValues=this;
		String one;
		String two;
		
		Integer some = 1;
		Integer that = 2;
	}
	
	class Bean2 {
		@SameValue(first="one", second="two")
		Bean2 thisValue=this;
		@SameValue(first="some", second="that")
		Bean2 thisValue2=this;
		String one;
		String two;
		
		Integer some = 1;
		Integer that = 2;
	}
	
	@Before
	public void setup() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		bean.one = "someValue";
		bean.two = "diferent";
	}
	
	@Test
	public void shouldValidateDiferentValue() throws Exception {
		Set<ConstraintViolation<Bean>> violations = validator.validate(bean);
		assertEquals(1, violations.size());
	}
	
	@Test
	public void shouldValidateSameValue() throws Exception {
		bean.two = bean.one;
		Set<ConstraintViolation<Bean>> violations = validator.validate(bean);
		assertEquals(0, violations.size());
	}
}
