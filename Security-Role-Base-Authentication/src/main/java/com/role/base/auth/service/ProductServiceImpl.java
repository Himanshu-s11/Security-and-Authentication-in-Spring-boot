package com.role.base.auth.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.role.base.auth.entity.Product;
import com.role.base.auth.repository.ProductRepo;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<Product> viewAllProduct() throws Exception {
		List<Product> list= productRepo.findAll();
		if(list.isEmpty()) {
			throw new Exception("Product not found exception");
		}
		return list;
	}

	@Override
	public Product addProduct(Product product) throws Exception{
		Product prod= new Product();
		prod.setPName(product.getPName());
		prod.setCategory(product.getCategory());
		prod.setPrice(product.getPrice());
		return productRepo.save(prod);
	}

	@Override
	public void deleteProduct(long id) throws Exception {
		productRepo.findById(id).orElseThrow(()-> new Exception());
		productRepo.deleteById(id);
		
	}


	@Override
	public Product editProduct(Product product, long id) throws Exception {
		Product pro= productRepo.findById(id).orElseThrow(()-> new Exception("Product not found"));
		pro.setPName(product.getPName());
		pro.setPrice(product.getPrice());
		return productRepo.save(pro);
	}

}
