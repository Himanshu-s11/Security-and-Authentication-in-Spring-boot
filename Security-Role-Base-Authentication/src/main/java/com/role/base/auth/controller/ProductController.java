package com.role.base.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.role.base.auth.entity.Product;
import com.role.base.auth.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	

	@GetMapping("/")
	public ModelAndView viewAllProducts() throws Exception {
		ModelAndView modelAndView = new ModelAndView("index");
		List<Product> prodList = productService.viewAllProduct();
		modelAndView.addObject("Product", prodList);
		return modelAndView;
	}

	@PostMapping("/add")
	public String addProduct(Product product) throws Exception {
		productService.addProduct(product);
		return "redirect:/";
	}
	
	@PostMapping("/edit/{id}")
	public String editProduct(@PathVariable ("id") long id, Product product) throws Exception {
		productService.editProduct(product, id);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") long id) throws Exception {
		productService.deleteProduct(id);
		return "redirect:/";
	}

	
}
