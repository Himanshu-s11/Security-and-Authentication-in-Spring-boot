package com.role.base.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.role.base.auth.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}
