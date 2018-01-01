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

import com.bestv.monitor.dao.MsgTemplateMapper;
import com.bestv.monitor.model.MsgTemplate;
import com.bestv.monitor.model.ValidateType;
import com.bestv.monitor.service.TemplateService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/template")
public class TemplateController extends BaseController {
	@Autowired
	TemplateService service;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("template", new MsgTemplate());
		model.addAttribute("type", ValidateType.getList());
		return "log/template";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		MsgTemplateMapper mapper = sqlSession
				.getMapper(MsgTemplateMapper.class);
		List<MsgTemplate> list = mapper.selectAll();
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid MsgTemplate template, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.add(template, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid MsgTemplate template, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.update(template, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}