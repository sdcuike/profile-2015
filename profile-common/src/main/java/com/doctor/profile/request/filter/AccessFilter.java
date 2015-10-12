package com.doctor.profile.request.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.doctor.profile.constant.DubboUrlParameter;
import com.doctor.profile.constant.WebConstant;
import com.doctor.web.security.UserIdCookieManger;

/**
 * 用户访问资源服务器web端 :访问日志(第一个执行)
 * 
 * @author cuike
 *
 * @time 2015年9月16日 下午8:08:53
 */
public class AccessFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 此MDC uuid 配置日志输出(追踪每条记录操作ID
		// MDC.put(uuid, UUID.randomUUID().toString()); //跟新id生成

		// 每次请求的id
		String requestId = UUID.randomUUID().toString();
		MDC.put(WebConstant.requestId, requestId);
		MDC.put(DubboUrlParameter.uuid, requestId);

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		// boolean contains = Splitter.on("/").splitToList(httpServletRequest.getRequestURL().toString()).contains(staticResource);
		String path = httpServletRequest.getRequestURL().toString();
		boolean contains = false;
		if (path.lastIndexOf(".") == -1) {
			contains = false;
		} else {
			contains = WebConstant.staticResource.contains(path.substring(path.lastIndexOf(".")));
		}

		if (contains == false) {
			UUID userId = UserIdCookieManger.getUserId(httpServletRequest, (HttpServletResponse) response);

			MDC.put(WebConstant.uuid, userId.toString());// 用户标识
			MDC.put(DubboUrlParameter.uuid, userId.toString());// 系统调用跟踪

			// 还需要哪些信息
			log.info("access log:{}", AcessInfo.getAcessInfo(httpServletRequest));

			try {
				chain.doFilter(request, response);
			} finally {
				MDC.remove(WebConstant.uuid);
				MDC.remove(WebConstant.requestId);
				MDC.remove(DubboUrlParameter.uuid);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {

	}

}
