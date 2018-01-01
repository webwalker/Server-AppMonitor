package com.bestv.monitor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts.Keys;
import bms.core.controller.BaseController;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;

import com.bestv.monitor.dao.MsgConfigMapper;
import com.bestv.monitor.model.ConfigType;
import com.bestv.monitor.model.MsgConfig;
import com.bestv.monitor.service.ConfigService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/config")
public class ConfigController extends BaseController {
	@Autowired
	ConfigService service;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("config", new MsgConfig());
		model.addAttribute("type", ConfigType.getList());
		return "log/config";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		MsgConfigMapper mapper = sqlSession.getMapper(MsgConfigMapper.class);
		List<MsgConfig> list = mapper.selectAll();
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid MsgConfig config, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.add(config, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid MsgConfig config, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.update(config, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}