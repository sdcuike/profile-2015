package com.doctor.web.security;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shizi
 *
 * @time 2015年9月22日 上午10:26:37
 */
public class UserIdCookieManger {
	public static final Logger log = LoggerFactory.getLogger(UserIdCookieManger.class);

	public static final String userid_cookie_name = "abDMPyFdPKwb9TUhASU7g";
	public static final String cookie_path = "/";
	public static final int cookie_maxage = 6 * 30 * 24 * 60 * 60;

	public static UUID getUserId(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			cookies = new Cookie[0];
		}
		for (Cookie cookie : cookies) {
			if (userid_cookie_name.equals(cookie.getName())) {
				// 已经有uuid
				String userId = cookie.getValue().trim();
				try {
					UUID uuid = UuidManger.decryptUuid(userId);
					request.setAttribute(userid_cookie_name, uuid.toString());

					log.info("访问用户id:{},生成时间:{}", uuid, com.doctor.web.security.UUID.timestamp(uuid));

					return uuid;

				} catch (Throwable e) {
					log.error("解密异常:{}", userId, e);
				}
			}
		}

		// 用户第一次访问，或清理过cookie

		UUID timeBasedUuid = UuidManger.getTimeBasedUuid();
		try {
			String encryptUuid = UuidManger.encryptUuid(timeBasedUuid);
			Cookie cookie = new Cookie(userid_cookie_name, encryptUuid);
			cookie.setPath(cookie_path);
			cookie.setMaxAge(cookie_maxage);
			// cookie.setHttpOnly(true);此cookie 客户端可以看到否
			response.addCookie(cookie);
			return timeBasedUuid;

		} catch (Throwable e) {
			log.error("加密异常:{}", timeBasedUuid, e);
		}

		return null;

	}

}
