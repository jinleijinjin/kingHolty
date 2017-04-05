package org.slsale.common;

public class SQLTools {
	/*
	 * 模糊查询的时候防止sql注入(字符替换)
	 * 
	 * */
	public static String transfer(String string){
		if (string.contains("%") || string.contains("_")) {
			string = string.replace("\\\\", "\\\\\\\\")
					.replaceAll("\\%", "\\\\%")
					.replaceAll("\\_", "\\\\_");
		}
		return string;
	}
}
