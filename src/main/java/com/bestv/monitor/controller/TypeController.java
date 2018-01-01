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

import com.bestv.monitor.dao.MsgTypesMapper;
import com.bestv.monitor.model.MsgTypes;
import com.bestv.monitor.service.TypeService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/type")
public class TypeController extends BaseController {
	@Autowired
	TypeService service;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("type", new MsgTypes());
		return "log/type";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		MsgTypesMapper mapper = sqlSession.getMapper(MsgTypesMapper.class);
		List<MsgTypes> list = mapper.selectAll();
		return getJson(list);
	}

	@RequestMapping("singlelist")
	@ResponseBody
	public List<MsgTypes> showLists(HttpServletRequest request) {
		MsgTypesMapper mapper = sqlSession.getMapper(MsgTypesMapper.class);
		List<MsgTypes> list = mapper.selectAll();
		return list;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid MsgTypes type, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.add(type, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid MsgTypes type, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.update(type, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}