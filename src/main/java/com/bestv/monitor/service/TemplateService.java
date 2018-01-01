package com.bestv.monitor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.model.MyResponse;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgTemplateMapper;
import com.bestv.monitor.model.MsgTemplate;

/**
 * @author xu.jian
 * 
 */
@Service
public class TemplateService extends BaseService {
	@Autowired
	MsgTemplateMapper mapper;

	public MsgTemplate getTemplate(int tempID) {
		return mapper.selectByPrimaryKey(tempID);
	}

	public MyResponse add(MsgTemplate template, BindingResult result) {
		try {
			mapper.insertSelective(template);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MsgTemplate template, BindingResult result) {
		template.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(template);
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
