package com.demo.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public final class TimeBasedUuidUtils {

	public static final String getUuid() {
		TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		UUID uuid = gen.generate();
		return uuid.toString();
	}

	public static final String getOrderedUuid() {
		String strUuid = getUuid();
		return StringUtils.join(StringUtils.substring(strUuid, 14, 18), StringUtils.substring(strUuid, 9, 13), StringUtils.substring(strUuid, 0, 8),
				StringUtils.substring(strUuid, 19, 23), StringUtils.substring(strUuid, 24));
	}

	public static void main(String[] args) {

		final Set<String> set = new HashSet<String>();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String uuid = getOrderedUuid();
				set.add(uuid);
				System.out.println(uuid);
				System.out.println("len:" + set.size());
			}
		};
		long s = System.currentTimeMillis();
		System.out.println(s);
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}
		long e = System.currentTimeMillis();
		System.out.println(e - s);

		// System.out.println(StringUtils.join("abc", "def"));

		// String strUuid = "58e0a7d7-eebc-11d8-9669-0800200c9a66";
		// String strResult = StringUtils.join(StringUtils.substring(strUuid, 14, 18), StringUtils.substring(strUuid, 9, 13), StringUtils.substring(strUuid, 0, 8),
		// StringUtils.substring(strUuid, 19, 23), StringUtils.substring(strUuid, 24));
		// System.out.println("---------------");
		// System.out.println(strResult);
		// System.out.println("---------------");
		// 11d8eebc58e0a7d796690800200c9a66
		// 11d8eebc58e0a7d796690800200c9a66

	}
}
