package com.bestv.monitor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts.Keys;
import bms.core.common.util.StringUtil;
import bms.core.controller.BaseController;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.service.GroupService;

import com.bestv.monitor.model.KeyValues;
import com.bestv.monitor.model.MsgLog;
import com.bestv.monitor.service.LogService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/log")
public class LogController extends BaseController {
	@Autowired
	LogService service;
	@Autowired
	GroupService gService;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		MsgLog log = new MsgLog();
		List<KeyValues> status = new ArrayList<KeyValues>();
		status.add(new KeyValues("全部", "2"));
		status.add(new KeyValues("非法", "0"));
		status.add(new KeyValues("合法", "1"));
		model.addAttribute("status", status);
		model.addAttribute("log", log);
		return "log/log";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(MsgLog log, HttpServletRequest request) {
		initQuery(log, request);
		int count = service.getLogsCount(log);
		return getJson(
				service.queryLogs(log, Integer.valueOf(pageNo),
						Integer.valueOf(pageSize)), count, pageNo);
	}

	@RequestMapping("down")
	@ResponseBody
	public MyResponse down(MsgLog log, HttpServletRequest request,
			HttpServletResponse response) {
		initQuery(log, request);
		List<MsgLog> list = service.queryLogs(log, Integer.valueOf(pageNo),
				Integer.valueOf(pageSize));
		return service.down(request, response, list);
	}

	@RequestMapping("mailgroup")
	@ResponseBody
	public List<Group> showMailGroup(HttpServletRequest request) {
		return gService.selectByType("mail");
	}

	@RequestMapping("mail")
	@ResponseBody
	public MyResponse mail(MsgLog log, HttpServletRequest request) {
		initQuery(log, request);
		List<MsgLog> list = service.queryLogs(log, Integer.valueOf(pageNo),
				Integer.valueOf(pageSize));
		return service.sendMail(request, list);
	}

	@RequestMapping(value = "clearlog", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse clearlog(@RequestParam("date") String date) {
		Date dt = StringUtil.getDate(date);
		if (dt == null)
			return msg.getFailed();
		service.clearLog(date);
		return msg.getSuccess();
	}

	public void initQuery(MsgLog log, HttpServletRequest request) {
		this.setPage(request);
		if (log.getMsgType() != null && !log.getMsgType().isEmpty())
			log.setType(Integer.valueOf(log.getMsgType()));
		// set msg status
		switch (log.getMsgStatus()) {
		case 0:
			log.setIsValid(false);
			break;
		case 1:
			log.setIsValid(true);
			break;
		default:
			log.setIsValid(null);
			break;
		}
	}
}