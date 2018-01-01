package com.bestv.monitor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu.jian
 * 
 */
public enum ValidateType {
	And(1, "同时满足"),

	Or(2, "任一满足");

	public final int code;
	public final String desc;

	ValidateType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static ValidateType getByCode(int code) {
		for (ValidateType resultCode : ValidateType.values()) {
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

	public static List<ValidateType> getList() {
		List<ValidateType> list = new ArrayList<ValidateType>();
		list.add(And);
		list.add(Or);
		return list;
	}
}
