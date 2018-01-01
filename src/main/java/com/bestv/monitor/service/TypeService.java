package com.bestv.monitor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.model.MyResponse;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgTypesMapper;
import com.bestv.monitor.model.MsgTypes;

/**
 * @author xu.jian
 * 
 */
@Service
public class TypeService extends BaseService {
	@Autowired
	MsgTypesMapper mapper;

	public MyResponse add(MsgTypes type, BindingResult result) {
		try {
			mapper.insertSelective(type);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MsgTypes type, BindingResult result) {
		type.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(type);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		int retCode = mapper.deleteByIDs(id.split(","));
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}
}
