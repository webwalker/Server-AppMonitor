package com.bestv.monitor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.model.MyResponse;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgReceiverMapper;
import com.bestv.monitor.model.MsgConfig;
import com.bestv.monitor.model.MsgReceiver;

/**
 * @author xu.jian
 * 
 */
@Service
public class ReceiverService extends BaseService {
	@Autowired
	MsgReceiverMapper mapper;

	public MyResponse add(MsgReceiver receiver, BindingResult result) {
		try {
			mapper.insertSelective(receiver);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MsgReceiver receiver, BindingResult result) {
		mapper.updateByPrimaryKeySelective(receiver);
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
