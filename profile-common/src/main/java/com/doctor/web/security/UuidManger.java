package com.doctor.web.security;

import java.util.UUID;

/**
 * @author shizi
 *
 * @time 2015年9月22日 上午9:50:00
 */
public final class UuidManger {

	// TODO：换key配置,是否加入版本号码。
	private static final String base64KeyString = "Bo7MO+wN8KOflKaAq76FrQ==";

	public static UUID getTimeBasedUuid() {
		return com.doctor.web.security.UUID.getTimeUUID();
	}

	public static UUID getUuidFromStringFormatUuid(String uuid) {
		return UUID.fromString(uuid);
	}

	public static String encryptUuid(UUID uuid) throws Exception {
		return AesUtil.encryptToBase64String(uuid.toString(), base64KeyString);
	}

	public static UUID decryptUuid(String encryptedBase64String) throws Exception {
		String decryptFromBase64String = AesUtil.decryptFromBase64String(encryptedBase64String, base64KeyString);
		return UUID.fromString(decryptFromBase64String);
	}

	public static void main(String[] args) throws Exception {
		UUID uuid = UuidManger.getTimeBasedUuid();
		System.out.println(uuid.toString());

		String encryptUuid = UuidManger.encryptUuid(uuid);
		System.out.println(encryptUuid);
		UUID uuid2 = UuidManger.decryptUuid(encryptUuid);
		System.out.println(uuid2.toString());

		System.out.println("---");
		System.out.println(UuidManger.encryptUuid(UuidManger.getUuidFromStringFormatUuid("5e1009e0-60d8-11e5-823e-e1af776c8c66")));
	}
}
