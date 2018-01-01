package com.bestv.monitor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.model.MyResponse;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgConfigMapper;
import com.bestv.monitor.model.MsgConfig;

/**
 * @author xu.jian
 * 
 */
@Service
public class ConfigService extends BaseService {
	@Autowired
	MsgConfigMapper mapper;

	public MyResponse add(MsgConfig config, BindingResult result) {
		try {
			mapper.insertSelective(config);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MsgConfig config, BindingResult result) {
		config.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(config);
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
