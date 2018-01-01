package com.bestv.monitor.dao;

import java.util.List;

import com.bestv.monitor.model.MsgConfig;
import com.bestv.monitor.model.MsgConfigJson;

public interface MsgConfigMapper {
	int deleteByIDs(String[] ids);

	List<MsgConfig> selectAll();

	List<MsgConfigJson> selectValids();

	int deleteByPrimaryKey(Integer ID);

	int insert(MsgConfig record);

	int insertSelective(MsgConfig record);

	MsgConfig selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgConfig record);

	int updateByPrimaryKey(MsgConfig record);
}