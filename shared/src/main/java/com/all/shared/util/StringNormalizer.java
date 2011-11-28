package com.all.shared.util;

import java.text.Normalizer;

public class StringNormalizer {

	private static final String KEYWORD_REGEX = "\\p{InCombiningDiacriticalMarks}+";

	public static String normalize(String word){
		return Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll(KEYWORD_REGEX, "");
	}
}
