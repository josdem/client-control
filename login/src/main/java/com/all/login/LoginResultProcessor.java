/**
 * 
 */
package com.all.login;

import com.all.app.Attributes;
import com.all.app.ResultProcessor;
import com.all.shared.model.User;

public class LoginResultProcessor implements ResultProcessor<User> {
	@Override
	public User result(Attributes attributes) {
		return (User) attributes.getAttribute(LoginAttributes.USER);
	}
}