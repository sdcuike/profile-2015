package com.doctor.profile.dubbo.extend.filter;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.alibaba.dubbo.rpc.RpcInvocation;
import com.doctor.profile.constant.DubboUrlParameter;
import com.doctor.profile.constant.WebConstant;

/**
 * 服务提供方过滤器
 * 
 * @author cuike
 *
 * @time 2015年10月9日 下午3:36:05
 */
// @Activate(group = { Constants.PROVIDER })
public class ProviderElapsedTimeFilter extends ElapsedTimeFilter {

	@Override
	protected String precessUuid(RpcInvocation invocation) {
		String dubboUUID = invocation.getAttachment(DubboUrlParameter.uuid);

		if (StringUtils.isBlank(dubboUUID)) {
			log.error("调用该服务的消费者没有配置uuid,用于跟踪系统调用功能");
			dubboUUID = "null";
		}

		String uuid = UUID.randomUUID().toString(); // 本系统uuid
		MDC.put(WebConstant.uuid, uuid); // 本系统业务日志，打印本系统id

		// dubboUUID = dubboUUID + "|" + uuid;// 前者系统id+本系统id
		dubboUUID = DubboUrlParameter.concat(dubboUUID, uuid);// 前者系统id+本系统id

		invocation.setAttachment(DubboUrlParameter.uuid, dubboUUID);
		MDC.put(DubboUrlParameter.uuid, dubboUUID);

		return dubboUUID; // 返回全路径id，filter打印一次
	}
}
