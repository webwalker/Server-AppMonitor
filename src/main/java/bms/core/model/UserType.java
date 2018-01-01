package bms.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu.jian
 * 
 */
public enum UserType {

	Member(-1, ""),
	
	Admin(1, "管理员"),	

	Ott(2, "平台"),

	Termial(3, "终端"),

	APK(4, "APK_VIS"),

	Support(5, "运维"),

	Cloud(6, "云平台"),

	CDN(7, "CDN");

	public final int code;
	public final String desc;

	UserType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static UserType getByCode(int code) {
		for (UserType resultCode : UserType.values()) {
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

	public static List<UserType> getList(UserType type) {
		List<UserType> list = new ArrayList<UserType>();
		switch (type) {
		case Admin:
			list.add(Admin);
			list.add(Ott);
			list.add(Termial);
			list.add(APK);
			list.add(Support);
			list.add(Cloud);
			list.add(Admin);
			break;
		default:
			break;
		}
		return list;
	}
}
