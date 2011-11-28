package com.all.client.model;

import java.io.Serializable;
import java.util.Comparator;

public class SizeComparator implements Comparator<String>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(String o1, String o2) {
		String[] splittedOne = o1.split(" ");
		String[] splittedTwo = o2.split(" ");
		FileSizeType fileSizeTypeOne = FileSizeType.valueOf(splittedOne[1]);
		FileSizeType fileSizeTypeTwo = FileSizeType.valueOf(splittedTwo[1]);
		int compareFileSizes = fileSizeTypeOne.compareTo(fileSizeTypeTwo);
		if (compareFileSizes != 0) {
			return compareFileSizes;
		}
		Double sizeOne = new Double(splittedOne[0]);
		Double sizeTwo = new Double(splittedTwo[0]);
		return sizeOne.compareTo(sizeTwo);
	}

}
