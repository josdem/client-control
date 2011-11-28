package com.all.client.model.predicate;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Locale;

import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.client.model.StringPredicate;
import com.all.client.util.Collations;


public abstract class BaseCollatorPredicate<T extends Object> implements Predicate {
	private static final Log log = LogFactory.getLog(StringPredicate.class);
	private String target;
	protected String rules;
	private RuleBasedCollator collator;

	protected BaseCollatorPredicate(String target) {
		this.target = target;
		initialize();
	}

	private void initialize() {
		rules = ((RuleBasedCollator) Collator.getInstance(Locale.US)).getRules();
		refineRules();

		try {
			collator = new RuleBasedCollator(rules);
			collator.setStrength(Collator.PRIMARY);
		} catch (ParseException e) {
			log.error("Error while parsing collator rules [" + rules + "]", e);
		}
	}
	
	protected abstract void refineRules();

	protected abstract String sourceString(T Object);

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(Object object) {
		String source = sourceString((T) object);
		return collatorContains(source);
	}

	private boolean collatorContains(String source) {
		if (source == null || collator == null) {
			return false;
		}
		return Collations.contains(collator, source, target);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ":" + target;
	}

}
