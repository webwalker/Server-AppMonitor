package com.bestv.monitor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu.jian
 * 
 */
public enum ConfigType {
	System(1, "系统配置"),

	Group(2, "分组配置");

	public final int code;
	public final String desc;

	ConfigType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static ConfigType getByCode(int code) {
		for (ConfigType resultCode : ConfigType.values()) {
			if (resultCode.getCode() == code) {
				return resultCode;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static List<ConfigType> getList() {
		List<ConfigType> list = new ArrayList<ConfigType>();
		list.add(System);
		list.add(Group);
		return list;
	}
}
