package com.bestv.monitor.model;

import java.util.Date;

/**
 * 固定的消息体定义
 * 
 * @author xu.jian
 * 
 */
public class MsgBody {
	public String tempCode; // 服务端分配的模板号, 同一个消息类型，可有多个模板
	public String userID;
	public String userGroup;
	public String area;
	public String osVersion;
	public String apkVersion;
	public String moduleUrl;
	public String payAgentUrl;
	public String appCode;
	public int type;
	public String label; // 用于识别的扩展标识位
	public Object data;
	public Object ext;
	public boolean isValid = true;
	public String message;
	public int createIndex;
	public Date createTime;
}
