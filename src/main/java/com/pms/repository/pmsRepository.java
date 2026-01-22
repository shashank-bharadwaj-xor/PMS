package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.model.Product;

public interface pmsRepository extends JpaRepository<Product, Long>
{


	

}
