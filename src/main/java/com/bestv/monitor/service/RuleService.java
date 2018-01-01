package com.bestv.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.model.MyResponse;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgRuleMapper;
import com.bestv.monitor.model.MsgRule;
import com.bestv.monitor.model.MsgTempRule;

/**
 * @author xu.jian
 * 
 */
@Service
public class RuleService extends BaseService {
	@Autowired
	MsgRuleMapper mapper;

	public MyResponse add(MsgRule rule, BindingResult result) {
		try {
			mapper.insertSelective(rule);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MsgRule rule, BindingResult result) {
		rule.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(rule);
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

	public MyResponse bind(int tempID, String ruleIDs, String all) {
		try {
			unbind(tempID, all);

			List<MsgTempRule> list = new ArrayList<MsgTempRule>();
			String[] ids = ruleIDs.split(",");
			for (String id : ids) {
				MsgTempRule rule = new MsgTempRule();
				rule.setID(tempID);
				rule.setRuleID(Integer.valueOf(id));
				list.add(rule);
			}
			mapper.bindIDs(list);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse unbind(int tempID, String authIDs) {
		if (tempID <= 0 || authIDs.isEmpty())
			return msg.getFailed();

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("tempID", tempID);
		maps.put("rules", authIDs.split(","));

		int retCode = mapper.unbindIDs(maps);
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}
}
