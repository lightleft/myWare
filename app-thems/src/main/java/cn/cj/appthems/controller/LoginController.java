package cn.cj.appthems.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cj.appthems.service.LoginService;

@Controller
public class LoginController {
	@Resource
	private LoginService loginService;

	@RequestMapping("/get")
	public String getUserById(Integer id, Model model) {
		model.addAttribute("user", loginService.getUserById(id));
		return "show";
	}
}
