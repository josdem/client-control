package com.all.client.util;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

public final class PredicateUtil {
	
	private PredicateUtil() {
		
	}
	
	public static Predicate mergeAndPredicate(List<Predicate> predicates) {
		if (predicates.size() > 1) {
			Predicate andPredicate = new AndPredicate(predicates.get(0), predicates.get(1));
			for (int i = 2; i < predicates.size(); i++) {
				andPredicate = new AndPredicate(andPredicate, predicates.get(i));
			}
			return andPredicate;
		} else {
			return predicates.get(0);
		}
	}
}
