package com.all.login;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import com.all.core.common.services.ApplicationDao;
import com.all.i18n.DefaultMessages;
import com.all.login.controller.LoginController;
import com.all.login.services.LoginMessageService;
import com.all.testing.SpringTestSuite;

public class TestSpringConfig extends SpringTestSuite {
	@Override
	public String[] configurations() {
		return new String[] { "/com/all/login/LoginAppContext.xml", "/core/common/CommonAppContext.xml" };
	}

	@Override
	public void initBeans() {
		testBean(LoginController.class);
		testBean(Validator.class);
		testBean(ApplicationDao.class);
		testBean(org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
		testBean(ApplicationDao.class);
		testBean(LoginMessageService.class);
	}

	@Override
	protected List<Object> additionalBeans() {
		ArrayList<Object> beans = new ArrayList<Object>();
		beans.add(new DefaultMessages());
		return beans;
	}

}
