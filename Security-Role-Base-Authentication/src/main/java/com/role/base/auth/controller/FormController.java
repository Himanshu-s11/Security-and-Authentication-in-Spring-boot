package com.role.base.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.role.base.auth.exception.UserExceptionNotFoundException;
import com.role.base.auth.service.UserDetailsServiceImpl;

import net.bytebuddy.utility.RandomString;


@Controller
public class FormController {
	
	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;

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
	//prevent from going back to login page
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
	
	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
	//	model.addAttribute("pageTitle","Forgot Password");
		return "forgot_password_page";
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPasswordForm(HttpServletRequest request, Model model) {
		String email=request.getParameter("email");
		String tocken=RandomString.make(45);
		try {
			detailsServiceImpl.udateResetPassword(tocken,email);
		} catch (UserExceptionNotFoundException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Email"+ email);
		System.out.println("Tockent"+ tocken);
		return "forgot_password_page";
	}
	
}
