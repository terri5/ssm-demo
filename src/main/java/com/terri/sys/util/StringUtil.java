package com.terri.sys.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String subStrForLastDot(String targetName) {
		if(StringUtils.isEmpty(targetName) ||targetName.lastIndexOf(".")<0)
		return null;
		else
		return 	targetName.substring(targetName.lastIndexOf(".")+1);
	}
}
