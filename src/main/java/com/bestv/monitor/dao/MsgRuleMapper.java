package com.bestv.monitor.dao;

import java.util.List;
import java.util.Map;

import com.bestv.monitor.model.MsgRule;
import com.bestv.monitor.model.MsgTempRule;

public interface MsgRuleMapper {
	int bindIDs(List<MsgTempRule> rule);

	int unbindIDs(Map<String, Object> map);

	int deleteByIDs(String[] ids);

	List<MsgRule> selectAll();

	List<MsgRule> selectAllWithTemplate(Integer ID);

	int deleteByPrimaryKey(Integer ID);

	int insert(MsgRule record);

	int insertSelective(MsgRule record);

	MsgRule selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgRule record);

	int updateByPrimaryKey(MsgRule record);
}