package com.role.base.auth.controller;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.role.base.auth.entity.Product;


@Controller
public class FormController {

	@GetMapping("/addProduct")
	public String showFormToaddToDo( Product prod, Model model) {
		model.addAttribute("todo", prod);
		return "addProduct";
	}
	
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable("id") long id ,Product product,Model model) throws Exception {
		model.addAttribute("todo",product);
		return "editProduct";
	}
	
	@GetMapping("/403")
	public String error() {
		return "403";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		
		if(auth==null || auth instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}
	
	@PostMapping("/login_success_handeler")
	public String loginSuccess() {
		
		return "index";
	}
	
}
