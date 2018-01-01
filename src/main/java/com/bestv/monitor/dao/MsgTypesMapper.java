package com.bestv.monitor.dao;

import java.util.List;

import com.bestv.monitor.model.MsgTypes;

public interface MsgTypesMapper {
	int deleteByIDs(String[] ids);

	List<MsgTypes> selectAll();

	int deleteByPrimaryKey(Integer ID);

	int insert(MsgTypes record);

	int insertSelective(MsgTypes record);

	MsgTypes selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgTypes record);

	int updateByPrimaryKey(MsgTypes record);
}