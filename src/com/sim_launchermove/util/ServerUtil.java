package com.sim_launchermove.util;

import java.lang.reflect.Method;

public class ServerUtil {

	/***
	 * �õ���ǰ�ֻ������к�
	 * 
	 * @return
	 */
	public static String getSerialNumber() {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

}
