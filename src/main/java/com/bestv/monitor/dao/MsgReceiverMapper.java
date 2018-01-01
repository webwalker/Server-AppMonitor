package com.bestv.monitor.dao;

import java.util.List;

import com.bestv.monitor.model.MsgReceiver;

public interface MsgReceiverMapper {
	int deleteByIDs(String[] ids);

	List<MsgReceiver> selectAll();

	int deleteByPrimaryKey(Integer ID);

	int insert(MsgReceiver record);

	int insertSelective(MsgReceiver record);

	MsgReceiver selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MsgReceiver record);

	int updateByPrimaryKey(MsgReceiver record);
}