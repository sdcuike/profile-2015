package com.doctor.profile.constant;

/**
 * 
 * @author cuike
 *
 * @time 2015年10月9日 下午3:22:36
 */

public final class DubboUrlParameter {
	// 跟踪系统调用url参数
	public static final String uuid = "dubboUUID";

	public static final String separator = "|";

	public static String concat(String a, String b) {
		return a + separator + b;
	}
}
