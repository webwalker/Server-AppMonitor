package com.bestv.monitor.model;

import java.util.Date;

public class MsgTemplate {
	private Integer ID;

	private String templateCode;

	private String templateName;

	private String ruleRegex;

	private Integer validateType;

	private Boolean isValid;

	private Date createTime;

	private Date updateTime;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode == null ? null : templateCode.trim();
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName == null ? null : templateName.trim();
	}

	public String getRuleRegex() {
		return ruleRegex;
	}

	public void setRuleRegex(String ruleRegex) {
		this.ruleRegex = ruleRegex;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Integer getValidateType() {
		return validateType;
	}

	public void setValidateType(Integer validateType) {
		this.validateType = validateType;
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