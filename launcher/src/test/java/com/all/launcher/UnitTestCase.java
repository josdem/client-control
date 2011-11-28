package com.all.launcher;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class UnitTestCase {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
}
