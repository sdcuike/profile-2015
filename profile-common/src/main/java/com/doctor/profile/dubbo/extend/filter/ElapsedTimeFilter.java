package com.doctor.profile.dubbo.extend.filter;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.doctor.profile.constant.DubboUrlParameter;
import com.doctor.profile.constant.WebConstant;

/**
 * 
 * @author cuike
 *
 * @time 2015年9月6日 下午1:40:05
 */
// @Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class ElapsedTimeFilter implements Filter {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String uuid = precessUuid((RpcInvocation) invocation);
		long start = System.currentTimeMillis();
		Result result = invoker.invoke(invocation);
		long elapsed = System.currentTimeMillis() - start;
		if (invoker.getUrl() != null) {
			// log.info("[" +invoker.getInterface() +"] [" + invocation.getMethodName() +"] [" + elapsed +"]" );
			String appName = invoker.getUrl().getParameter(Constants.APPLICATION_KEY);

			log.info("[{}],[{}] , [{}], [{}], {}, [{}], [{}], [{}ms]  ", appName, uuid, invoker.getInterface(), invocation.getMethodName(), Arrays.toString(invocation.getArguments()), result.getValue(), result.getException(), elapsed);
		}

		MDC.remove(WebConstant.requestId);
		MDC.remove(DubboUrlParameter.uuid);

		return result;
	}

	protected String precessUuid(RpcInvocation invocation) {
		return null;
	}

}
