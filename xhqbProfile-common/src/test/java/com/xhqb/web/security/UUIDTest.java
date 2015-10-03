package com.xhqb.web.security;

import java.util.Locale;
import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * 
 * @author shizi
 *
 * @time 2015年9月22日 上午9:11:13
 */
public class UUIDTest {

	// TODO:
	@Test
	public void testGetTimeUUID() {
		UUID timeUUID = com.xhqb.web.security.UUID.getTimeUUID();
		System.out.println(com.xhqb.web.security.UUID.timestamp(timeUUID));
		LocalDateTime localDateTime = com.xhqb.web.security.UUID.getLocalDateTime(timeUUID);
		System.out.println(localDateTime.toString("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
	}

}
