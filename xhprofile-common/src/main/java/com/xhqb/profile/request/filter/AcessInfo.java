package com.xhqb.profile.request.filter;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.util.LinkedMultiValueMap;

import com.alibaba.fastjson.JSON;

/**
 * @author cuike
 *
 * @time 2015年9月17日 下午4:43:18
 */
public class AcessInfo {
	// TODO:
	private String remoteAddr;
	private String remoteHost;
	private int remotePort;
	private String requestURL;
	private String localAddr;
	private String localName;
	private int localPort;

	private String queryString;
	private Cookie[] cookies;
	private String httpProtocol;
	private LinkedMultiValueMap<String, String> headerValues = new LinkedMultiValueMap<>();
	private String serverName;
	private int serverPort;
	private String servletPath;
	private Authentication authentication;

	private static TokenExtractor tokenExtractor = new BearerTokenExtractor();

	public static AcessInfo getAcessInfo(HttpServletRequest request) {
		AcessInfo acessInfo = new AcessInfo();
		acessInfo.remoteAddr = request.getRemoteAddr();
		acessInfo.remoteHost = request.getRemoteHost();
		acessInfo.remotePort = request.getRemotePort();
		acessInfo.requestURL = request.getRequestURL().toString();
		acessInfo.localAddr = request.getLocalAddr();
		acessInfo.localName = request.getLocalName();
		acessInfo.localPort = request.getLocalPort();
		acessInfo.queryString = request.getQueryString();
		acessInfo.cookies = request.getCookies();

		acessInfo.httpProtocol = request.getProtocol();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String hName = headerNames.nextElement();
			String hValue = request.getHeader(hName);
			acessInfo.headerValues.add(hName, hValue);
		}

		acessInfo.serverName = request.getServerName();
		acessInfo.serverPort = request.getServerPort();
		acessInfo.servletPath = request.getServletPath();
		acessInfo.authentication = tokenExtractor.extract(request);
		return acessInfo;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public String getLocalAddr() {
		return localAddr;
	}

	public String getLocalName() {
		return localName;
	}

	public int getLocalPort() {
		return localPort;
	}

	public String getQueryString() {
		return queryString;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public LinkedMultiValueMap<String, String> getHeaderValues() {
		return headerValues;
	}

	public String getServerName() {
		return serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getServletPath() {
		return servletPath;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public String getHttpProtocol() {
		return httpProtocol;
	}
}
