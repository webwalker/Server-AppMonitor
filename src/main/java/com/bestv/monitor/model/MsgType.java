package com.bestv.monitor.model;

/**
 * @author xu.jian
 * 
 */
public enum MsgType {
	Null(-1, ""),

	Module(1, "调度信息"),

	Auth(2, "鉴权信息"),

	Order(3, "订购信息"),

	Token(4, "CDNToken信息"),

	Play(5, "播放信息"),

	Vis(6, "调用信息、资源请求错误信息等"),

	Pay(7, "支付信息"),

	Step(8, "关键步骤信息"),

	Other(9, "其他类型");

	private final int code;
	private final String message;

	MsgType(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static MsgType getByCode(int code) {
		for (MsgType resultCode : MsgType.values()) {
			if (resultCode.getCode() == code) {
				return resultCode;
			}
		}

		return Null;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
