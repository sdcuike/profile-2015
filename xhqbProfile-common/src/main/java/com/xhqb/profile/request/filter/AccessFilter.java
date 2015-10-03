package com.xhqb.profile.request.filter;

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

import com.google.common.base.Splitter;
import com.xhqb.web.security.UserIdCookieManger;

/**
 * 用户访问资源服务器web端 :访问日志(第一个执行)
 * 
 * @author shizi
 *
 * @time 2015年9月16日 下午8:08:53
 */
public class AccessFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(AccessFilter.class);

	private static final String uuid = "uuid";
	private static final String staticResource = "resources";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 此MDC uuid 配置日志输出(追踪每条记录操作ID
		// MDC.put(uuid, UUID.randomUUID().toString()); //跟新id生成
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		boolean contains = Splitter.on("/").splitToList(httpServletRequest.getRequestURL().toString()).contains(staticResource);

		if (contains == false) {
			UUID userId = UserIdCookieManger.getUserId(httpServletRequest, (HttpServletResponse) response);
			MDC.put(uuid, userId.toString());

			// 还需要哪些信息
			log.info("access log:{}", AcessInfo.getAcessInfo(httpServletRequest));

			try {
				chain.doFilter(request, response);
			} finally {
				MDC.remove(uuid);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {

	}

}
