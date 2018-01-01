package com.bestv.monitor.dao;

import java.util.List;
import java.util.Map;

import com.bestv.monitor.model.MsgLog;

public interface MsgLogMapper {

	MsgLog selectLogsCount(Map<String, Object> map);

	List<MsgLog> selectLogs(Map<String, Object> map);

	int deleteByPrimaryKey(Integer ID);
	
	int deleteBeforeDate(String date);

	int insert(MsgLog record);

	int insertSelective(MsgLog record);

	MsgLog selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgLog record);

	int updateByPrimaryKeyWithBLOBs(MsgLog record);

	int updateByPrimaryKey(MsgLog record);
}