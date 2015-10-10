package com.doctor.web.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.cassandra.utils.UUIDGen;
import org.joda.time.LocalDateTime;

/**
 * 
 * @author shzii
 *
 * @time 2015年9月21日 下午4:23:53
 */
public final class UUID {

	public static java.util.UUID getTimeUUID() {
		return UUIDGen.getTimeUUID();
	}

	public static long getTimestamp(java.util.UUID uuid) {
		return UUIDGen.getAdjustedTimestamp(uuid);
	}

	public static String timestamp(java.util.UUID uuid) {
		long timestamp = UUIDGen.getAdjustedTimestamp(uuid);
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(timestamp);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(instance.getTime());
	}

	public static LocalDateTime getLocalDateTime(java.util.UUID uuid) {
		long timestamp = UUIDGen.getAdjustedTimestamp(uuid);
		return new LocalDateTime(timestamp);
		// localDateTime.toString("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	}

}
