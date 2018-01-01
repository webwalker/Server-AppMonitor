package com.bestv.monitor.dao;

import com.bestv.monitor.model.MsgTempRule;

public interface MsgTempRuleMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(MsgTempRule record);

    int insertSelective(MsgTempRule record);

    MsgTempRule selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(MsgTempRule record);

    int updateByPrimaryKey(MsgTempRule record);
}