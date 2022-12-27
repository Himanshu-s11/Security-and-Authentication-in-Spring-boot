package com.role.base.auth.service;

import java.util.List;

import com.role.base.auth.entity.Product;

public interface ProductService {

	public List<Product> viewAllProduct() throws Exception;
	Product addProduct(Product product) throws Exception;
	public void deleteProduct(long id) throws Exception;
	public Product editProduct(Product product,long id) throws Exception; 
}
