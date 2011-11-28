package com.all.shared.util;

public class NickNameRefiner {
	
	public static String refine(String nick){
		return nick.replaceAll("[0-9]","").replace("_", "").replace("-", "").replace(".", "");
	}
	
	public static boolean isNullOrEmpty(String key){
		if (key == null){
			return true;
		}
		else if(key.equals("")){
			return true;
		}
		return false;
	}

}
