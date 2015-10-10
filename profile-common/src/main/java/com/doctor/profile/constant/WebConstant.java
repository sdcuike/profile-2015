package com.doctor.profile.constant;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @author cuike
 *
 * @time 2015年10月9日 下午6:14:02
 */
public final class WebConstant {

	// web 过滤器中，在cookie中设置用户访问唯一标识
	public static final String uuid = "uuid";

	public static final String requestId = "requestId";

	// 自己添加静态资源类型
	public static final Set<String> staticResource = Sets.newHashSet(".css", ".js", ".jpg");
}
