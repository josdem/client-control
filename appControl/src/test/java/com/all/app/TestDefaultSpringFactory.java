package com.all.app;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

public class TestDefaultSpringFactory {
	@Test
	public void shouldTestFactory() throws Exception {
		SpringFactory factory = new DefaultSpringFactory();
		assertNotNull(factory.newGenericApplicationContext(new BeanCreationMonitor()));
		GenericApplicationContext context = factory.newGenericApplicationContext(null);
		assertNotNull(context);
		assertNotNull(factory.newLocalVariableTableParameterNameDiscoverer());
		assertNotNull(factory.newQualifierAnnotationAutowireCandidateResolver());
		assertNotNull(factory.newXmlBeanDefinitionReader(context));
	}
}
