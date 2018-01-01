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
import com.bestv.monitor.dao.MsgReceiverMapper;
import com.bestv.monitor.model.ConfigType;
import com.bestv.monitor.model.MsgConfig;
import com.bestv.monitor.model.MsgReceiver;
import com.bestv.monitor.service.ReceiverService;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/receiver")
public class ReceiverController extends BaseController {
	@Autowired
	ReceiverService service;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("receiver", new MsgReceiver());
		return "log/receiver";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		MsgReceiverMapper mapper = sqlSession
				.getMapper(MsgReceiverMapper.class);
		List<MsgReceiver> list = mapper.selectAll();
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid MsgReceiver receiver, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.add(receiver, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid MsgReceiver receiver, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		return service.update(receiver, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}