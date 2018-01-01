package bms.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.model.User;
import bms.core.service.LoginService;

/**
 * 登录管理
 * 
 * @author xu.jian
 * 
 */
@Controller
@SessionAttributes("user")
public class LoginController extends BaseController {
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return Consts.Path.login;
	}

	// 自动保存到Session中，直到setComplete
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") User user,
			BindingResult result, HttpServletRequest request, Model model) {
		if (result.hasErrors())
			return msg.toError(model, result.getFieldError());
		return loginService.login(user, result, request, model);
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		auths.logout();
		return Consts.Path.login;
	}

	@RequestMapping(value = "/permission")
	public String permission(Model model) {
		return Consts.Path.permission;
	}

	@RequestMapping(value = Keys.PATH_PREFIX + "/home")
	public String home(Model model) {
		return Consts.Path.home;
	}

	@RequestMapping(value = Keys.PATH_PREFIX + "/welcome")
	public String welcome(Model model) {
		return Consts.Path.welcome;
	}
}
