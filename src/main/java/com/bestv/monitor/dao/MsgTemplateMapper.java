package com.bestv.monitor.dao;

import java.util.List;

import com.bestv.monitor.model.MsgTemplate;

public interface MsgTemplateMapper {
	int deleteByIDs(String[] ids);

	List<MsgTemplate> selectAll();

	List<MsgTemplate> selectValids();

	int deleteByPrimaryKey(Integer ID);

	int insert(MsgTemplate record);

	int insertSelective(MsgTemplate record);

	MsgTemplate selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgTemplate record);

	int updateByPrimaryKey(MsgTemplate record);
}