package com.bestv.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts.Keys;
import bms.core.controller.BaseController;
import bms.core.model.MyResponse;

import com.bestv.monitor.model.MsgConfigJson;
import com.bestv.monitor.service.AppService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.SITE_PREFIX)
public class AppController extends BaseController {
	@Autowired
	AppService service;

	@RequestMapping(value = "getconfig", method = RequestMethod.GET)
	@ResponseBody
	public List<MsgConfigJson> init() {
		return service.getConfigs();
	}

	@RequestMapping(value = "others", method = RequestMethod.GET)
	public String others() {
		return "log/others";
	}

	@RequestMapping(value = "reconfig", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse config() {
		service.refreshConfig();
		return msg.getSuccess();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam("msg") String msg) {
		if (msg.isEmpty()) {
			System.out.println("input message is null.");
			return "log";
		}
		service.saveLog(msg);
		return "log";
	}
}