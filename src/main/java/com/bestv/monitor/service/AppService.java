package com.bestv.monitor.service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.common.Consts.Messages;
import bms.core.common.Loggers;
import bms.core.common.util.JacksonJsonUtil;
import bms.core.common.util.MessageUtil;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgConfigMapper;
import com.bestv.monitor.dao.MsgLogMapper;
import com.bestv.monitor.dao.MsgTemplateMapper;
import com.bestv.monitor.model.MsgBody;
import com.bestv.monitor.model.MsgConfigJson;
import com.bestv.monitor.model.MsgLog;
import com.bestv.monitor.model.MsgTemplate;

/**
 * @author xu.jian
 * 
 */
@Service
public class AppService extends BaseService {

	@Autowired
	MsgConfigMapper mapper;
	@Autowired
	MsgLogMapper logMapper;
	@Autowired
	MsgTemplateMapper template;
	@Autowired
	MessageUtil messageUtil;

	static List<MsgConfigJson> configs = new ArrayList<MsgConfigJson>();
	static List<MsgTemplate> templates = new ArrayList<MsgTemplate>();
	static ExecutorService executor = Executors.newCachedThreadPool();
	static boolean onlyErrorLog = false;

	public List<MsgConfigJson> getConfigs() {
		Loggers.info("getConfigs...");
		if (configs.size() > 0)
			return configs;
		configs = mapper.selectValids();
		return configs;
	}

	public void refreshConfig() {
		Loggers.info("refreshConfig...");
		configs.clear();
		templates.clear();
		onlyErrorLog = false;
	}

	public void saveLog(final String message) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				MsgBody msg = null;
				try {
					String jsonMsg = URLDecoder.decode(message, "UTF-8");
					Loggers.info("saveLog:" + jsonMsg);
					msg = (MsgBody) JacksonJsonUtil.jsonToBean(jsonMsg,
							MsgBody.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (msg == null) {
					Loggers.error("inputs is invalid.");
					return;
				}
				asyncTask(msg);
			}
		});
	}

	public void asyncTask(MsgBody body) {
		if (body.isValid == false) {
			saveDbLog(body);
			return;
		}
		// 按模板校验规则
		body.isValid = checkTemplateRule(body);
		// 模板为空不校验则状态合法
		if (body.tempCode.equals(""))
			body.isValid = true;
		// 只保存错误消息到数据库
		boolean onlyError = onlyErrorLog();
		if (onlyError) {
			if (body.isValid)
				return;
			Loggers.info("only record error log.");
			saveDbLog(body);
		} else {
			saveDbLog(body);
		}
	}

	public boolean checkTemplateRule(MsgBody body) {
		Loggers.info("check template rules..");
		List<MsgTemplate> rules = getTemplateRules(body.tempCode);
		if (body.tempCode.equals("") || rules.size() == 0) {
			Loggers.info("template code isn't existed.");
			return false;
		}

		if (body.data == null || body.data.toString().equals("")
				|| body.data.toString().equals("{}")) {
			Loggers.info("message is empty.");
			body.message = messageUtil.getMessage(Messages.MONITOR_EMPTY);
			return false;
		}

		for (MsgTemplate mt : rules) {
			Pattern pattern = Pattern.compile(mt.getRuleRegex());
			Matcher matcher = pattern.matcher(body.data.toString());
			if (!matcher.find()) {
				body.message = "rules is not invalid.";
				return false;
			}
		}

		return true;
	}

	// 获得模板规则
	public List<MsgTemplate> getTemplateRules(String templateCode) {
		if (templates.size() == 0)
			templates = template.selectValids();
		List<MsgTemplate> list = new ArrayList<MsgTemplate>();
		templateCode = templateCode.toUpperCase();
		for (MsgTemplate mt : templates) {
			if (mt.getTemplateCode().toUpperCase().equals(templateCode)) {
				list.add(mt);
			}
		}
		return list;
	}

	public void saveDbLog(MsgBody m) {
		MsgLog log = new MsgLog();
		log.setUserID(m.userID);
		log.setUserGroup(m.userGroup);
		log.setArea(m.area);
		log.setOsVersion(m.osVersion);
		log.setApkVersion(m.apkVersion);
		log.setModuleUrl(m.moduleUrl);
		log.setPayAgentUrl(m.payAgentUrl);
		log.setAppCode(m.appCode);
		log.setTempCode(m.tempCode);
		log.setType(m.type);
		log.setLabel(m.label);
		if (m.data == null)
			m.data = "";
		log.setData(m.data.toString());
		if (m.ext == null)
			m.ext = "";
		log.setExt(m.ext.toString());
		log.setIsValid(m.isValid);
		log.setMessage(m.message);
		log.setCreateIndex(m.createIndex);
		log.setCreateTime(m.createTime);

		int retCode = logMapper.insert(log);
	}

	public boolean onlyErrorLog() {
		if (onlyErrorLog)
			return onlyErrorLog;
		List<MsgConfigJson> list = getConfigs();
		for (MsgConfigJson msg : list) {
			if (msg.getConfigKey().toLowerCase().equals("onlyerrorlog")) {
				onlyErrorLog = msg.getConfigValue().equals("1") ? true : false;
			}
		}
		return onlyErrorLog;
	}
}