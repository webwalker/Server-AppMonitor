package com.bestv.monitor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import bms.core.common.Consts.Keys;
import bms.core.controller.BaseController;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;

import com.bestv.monitor.dao.MsgRuleMapper;
import com.bestv.monitor.model.MsgRule;
import com.bestv.monitor.model.MsgTemplate;
import com.bestv.monitor.model.ValidateType;
import com.bestv.monitor.service.RuleService;
import com.bestv.monitor.service.TemplateService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/rule")
@SessionAttributes("template")
public class RuleController extends BaseController {
	@Autowired
	RuleService service;
	@Autowired
	TemplateService tService;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("rule", new MsgRule());
		model.addAttribute("type", ValidateType.getList());
		model.addAttribute("template", new MsgTemplate());
		return "log/rule";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String usergroup(@PathVariable int id, Model model) {
		MsgTemplate template = tService.getTemplate(id);
		model.addAttribute("rule", new MsgRule());
		model.addAttribute("template", template);
		return "log/rule";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(@ModelAttribute("template") MsgTemplate template,
			HttpServletRequest request) {
		this.initPage(request);
		MsgRuleMapper mapper = sqlSession.getMapper(MsgRuleMapper.class);
		List<MsgRule> list = null;
		if (template.getID() == null) {
			list = mapper.selectAll();
		} else {
			list = mapper.selectAllWithTemplate(template.getID());
		}
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid MsgRule rule, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.add(rule, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid MsgRule rule, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.update(rule, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}

	@RequestMapping("bind/{tempID}/{ids}/{all}")
	@ResponseBody
	public MyResponse add(@PathVariable int tempID, @PathVariable String ids,
			@PathVariable String all) {
		return service.bind(tempID, ids, all);
	}
}