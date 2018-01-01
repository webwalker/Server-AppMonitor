package com.bestv.monitor.model;

import java.util.Date;

public class MsgRule {
	private Integer ID;

	private String ruleName;

	private String ruleRegex;

	private String message;

	private Integer tempID;

	private Boolean isValid;

	private Date createTime;

	private Date updateTime;

	private boolean ck;

	public boolean isCk() {
		if (tempID != null) {
			return true;
		}
		return false;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName == null ? null : ruleName.trim();
	}

	public String getRuleRegex() {
		return ruleRegex;
	}

	public void setRuleRegex(String ruleRegex) {
		this.ruleRegex = ruleRegex == null ? null : ruleRegex.trim();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public Integer getTempID() {
		return tempID;
	}

	public void setTempID(Integer tempID) {
		this.tempID = tempID;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}