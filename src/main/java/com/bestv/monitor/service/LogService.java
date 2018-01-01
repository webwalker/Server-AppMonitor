package com.bestv.monitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableSheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.common.excel.ExcelUtil;
import bms.core.common.util.HttpUtils;
import bms.core.common.util.StringUtil;
import bms.core.dao.UserGroupMapper;
import bms.core.mail.HtmlMailSender;
import bms.core.model.MyResponse;
import bms.core.model.UserGroup;
import bms.core.service.BaseService;

import com.bestv.monitor.dao.MsgLogMapper;
import com.bestv.monitor.model.MsgLog;

/**
 * @author xu.jian
 * 
 */
@Service
public class LogService extends BaseService {

	@Autowired
	MsgLogMapper logMapper;
	@Autowired
	UserGroupMapper groupMapper;

	public int getLogsCount(MsgLog log) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("log", log);
		MsgLog result = logMapper.selectLogsCount(map);
		return result.getLogCount();
	}

	public List<MsgLog> queryLogs(MsgLog log, int pageNo, int pageSize) {
		pageNo -= 1;
		int rows = pageNo * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("log", log);
		map.put("rows", rows);
		map.put("pageSize", pageSize);

		MsgLogMapper mapper = sqlSession.getMapper(MsgLogMapper.class);
		return mapper.selectLogs(map);
	}

	public void clearLog(String date) {
		logMapper.deleteBeforeDate(date);
	}

	public MyResponse down(HttpServletRequest request,
			HttpServletResponse response, List<MsgLog> list) {
		String saveName = StringUtil.getDateFileName() + ".xls";
		String path = saveToExcel(request, list, saveName);
		boolean ret = HttpUtils.download(path, StringUtil.getDateFileName()
				+ ".xls", request, response);
		if (!ret)
			return msg.getFailed();
		return msg.getSuccess();
	}

	public MyResponse sendMail(HttpServletRequest request, List<MsgLog> list) {
		String ids = request.getParameter("ids");
		String[] groupIDs = ids.split(",");
		List<UserGroup> users = groupMapper.selectUsersByGroupIDs(groupIDs);
		String[] recepts = new String[users.size()];
		for (int i = 0; i < users.size(); i++) {
			UserGroup ug = users.get(i);
			recepts[i] = ug.getEmail();
		}

		if (recepts.length == 0)
			return msg.getFailed();

		String saveName = StringUtil.getDateFileName() + ".xls";
		String path = saveToExcel(request, list, saveName);
		HtmlMailSender sender = new HtmlMailSender("Monitor Info",
				"error comes!", recepts);
		sender.addAttachment(HttpUtils.getRootPath(request) + "/files/"
				+ saveName);
		sender.send();

		return msg.getSuccess();
	}

	public String saveToExcel(HttpServletRequest request, List<MsgLog> list,
			String saveName) {
		String path = request.getSession().getServletContext().getRealPath("/");
		String savePath = path + "\\files\\";

		ExcelUtil excel = new ExcelUtil();
		WritableSheet ws = excel.createTemplateSheet(path
				+ "\\templates\\log.xls", savePath, saveName);
		for (int i = 0; i < list.size(); i++) {
			MsgLog log = list.get(i);
			excel.addTempCell(i + 1, 0, log.getID());
			excel.addTempCell(i + 1, 1, log.getUserID());
			excel.addTempCell(i + 1, 2, log.getUserGroup());
			excel.addTempCell(i + 1, 3, log.getArea());
			excel.addTempCell(i + 1, 4, log.getOsVersion());
			excel.addTempCell(i + 1, 5, log.getApkVersion());
			excel.addTempCell(i + 1, 6, log.getAppCode());
			excel.addTempCell(i + 1, 7, log.getMsgType());
			excel.addTempCell(i + 1, 8, log.getLabel());
			excel.addTempCell(i + 1, 9, log.getTempCode());
			excel.addTempCell(i + 1, 10, log.getModuleUrl());
			excel.addTempCell(i + 1, 11, log.getPayAgentUrl());
			excel.addTempCell(i + 1, 12, log.getMessage());
			excel.addTempCell(i + 1, 13, log.getData());
			excel.addTempCell(i + 1, 14, log.getExt());
			excel.addTempCell(i + 1, 15, log.getIsValid());
			excel.addTempCell(i + 1, 16, log.getCreateTime());
		}
		excel.createExcel();

		return savePath + saveName;
	}
}
