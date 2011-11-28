package com.all.client.model;

import com.all.client.model.predicate.BaseCollatorPredicate;

public abstract class StringPredicate<T extends Object> extends BaseCollatorPredicate<T> {

	protected StringPredicate(String target) {
		super(target);
	}

	protected void refineRules() {
		rules = rules.replaceFirst("<'@'", ""); // to ommit @ characters
		rules = rules + ",' ','@'";
	}


}
