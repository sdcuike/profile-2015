package com.xhqb.profile.dubbo.extend.filter;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月6日 下午1:40:05
 */
// @Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class ElapsedTimeFilter implements Filter {

	private static Logger log = LoggerFactory
			.getLogger(ElapsedTimeFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		long start = System.currentTimeMillis();
		Result result = invoker.invoke(invocation);
		long elapsed = System.currentTimeMillis() - start;
		if (invoker.getUrl() != null) {

			// log.info("[" +invoker.getInterface() +"] [" + invocation.getMethodName() +"] [" + elapsed +"]" );
			log.info("[{}], [{}], {}, [{}], [{}], [{}]   ", invoker.getInterface(), invocation.getMethodName(), Arrays.toString(invocation.getArguments()), result.getValue(), result.getException(), elapsed);

		}
		return result;
	}

}
