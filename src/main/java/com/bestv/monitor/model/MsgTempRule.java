package com.bestv.monitor.model;

import java.util.Date;

public class MsgTempRule {
    private Integer ID;

    private Integer tempID;

    private Integer ruleID;

    private Date createTime;

    private Date updateTime;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getTempID() {
        return tempID;
    }

    public void setTempID(Integer tempID) {
        this.tempID = tempID;
    }

    public Integer getRuleID() {
        return ruleID;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
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