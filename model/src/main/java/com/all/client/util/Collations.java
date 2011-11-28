/*
 * eXist Open Source Native XML Database
 * Copyright (C) 2004-2007 The eXist Project
 * http://exist-db.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *  
 *  $Id: Collations.java 6367 2007-08-16 08:53:10Z ellefj $
 */
package com.all.client.util;

import java.text.CollationElementIterator;
import java.text.RuleBasedCollator;

/**
 * Utility methods dealing with collations.
 * 
 * @author wolf
 */
public class Collations {

	// The definitive missing constant in java.lang.String
	static final int STRING_NOT_FOUND = -1;

	public static boolean contains(RuleBasedCollator collator, String target, String pattern) {
		final CollationElementIterator targetIter = collator.getCollationElementIterator(target);
		final CollationElementIterator patternIter = collator.getCollationElementIterator(pattern);
		return collationContains(targetIter, patternIter);
	}

	private static boolean collationStartsWith(CollationElementIterator targetIter, CollationElementIterator patternIter) {
		// log.debug(targetIter.toString() + " " + patternIter.toString());
		// Copied from Saxon
		while (true) {
			int targetIndex, patternIndex;
			do {
				patternIndex = patternIter.next();
			} while (patternIndex == 0);
			if (patternIndex == -1) {
				return true;
			}
			do {
				targetIndex = targetIter.next();
			} while (targetIndex == 0);
			if (targetIndex != patternIndex) {
				return false;
			}
		}
		// End of copy
	}

	private static boolean collationContains(CollationElementIterator targetIter, CollationElementIterator patternIter) {
		// Copy from Saxon
		int targetIndex, patternIndex;
		do {
			patternIndex = patternIter.next();
		} while (patternIndex == 0);
		if (patternIndex == -1) {
			return true;
		}
		targetIndex = -1;
		while (true) {
			// scan the first string to find a matching character
			while (targetIndex != patternIndex) {
				do {
					targetIndex = targetIter.next();
				} while (targetIndex == 0);
				if (targetIndex == -1) {
					// hit the end, no match
					return false;
				}
			}
			// matched first character, note the position of the possible match
			int start = targetIter.getOffset();
			if (collationStartsWith(targetIter, patternIter)) {
				return true;
			}
			// reset the position and try again
			targetIter.setOffset(start);
			targetIter.next(); // advance so the prevent cycling in the same position 

			patternIter.reset();
			targetIndex = -1;
			do {
				patternIndex = patternIter.next();
			} while (patternIndex == 0);
			// loop round to try again
		}
		// End of copy
	}

}
