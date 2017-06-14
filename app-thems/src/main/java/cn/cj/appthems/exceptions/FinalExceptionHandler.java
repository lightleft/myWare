package cn.cj.appthems.exceptions;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 404页面
 */
@Controller
public class FinalExceptionHandler implements ErrorController {

	@RequestMapping(value = "/errorC")
	public String error(Model model) {
		// 错误处理逻辑
		model.addAttribute("msg", "404 error");
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/errorC";
	}
}
