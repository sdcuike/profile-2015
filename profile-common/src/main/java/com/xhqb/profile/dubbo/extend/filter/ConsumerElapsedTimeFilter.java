package com.xhqb.profile.dubbo.extend.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.alibaba.dubbo.rpc.RpcInvocation;
import com.xhqb.profile.constant.DubboUrlParameter;

/**
 * 
 * @author cuike
 *
 * @time 2015年10月9日 下午4:02:31
 */
// @Activate(group = { Constants.CONSUMER })
public class ConsumerElapsedTimeFilter extends ElapsedTimeFilter {

	@Override
	protected String precessUuid(RpcInvocation invocation) {

		String dubboUUID = MDC.get(DubboUrlParameter.uuid);// 从mdc里面获取uuid

		if (StringUtils.isBlank(dubboUUID)) {
			log.error("该系统没设置dubbo uuid,用于跟踪系统调用功能");
			dubboUUID = "null";
		}

		invocation.setAttachment(DubboUrlParameter.uuid, dubboUUID);

		return dubboUUID; // 返回全路径id，filter打印一次
	}
}
