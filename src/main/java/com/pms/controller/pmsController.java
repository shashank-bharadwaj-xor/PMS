package com.pms.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pms.model.Product;
import com.pms.service.pmsService;

@RestController
@RequestMapping("/api")
public class pmsController 
{
	@Autowired
	pmsService ps;
	
	@PostMapping("/insert")
	public List<Product> insertData(@RequestBody List<Product> product)
	{
		return ps.insertData(product);
	}
	@GetMapping("/get")
	public List<Product> getdata()
	{
		return ps.getData();
	}
	@GetMapping("/get/{id}")
	public Product getDataById(@PathVariable Long id) {
	    return ps.getDataById(id);
	}
	
	@PutMapping("/update/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
	    return ps.updateProduct(id, updatedProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable Long id) {
	    ps.deleteProduct(id);   
	}
	@GetMapping("/getby/{category}")
	public List<Product> getDataByCategory(@PathVariable String category) {
	    return ps.getDataByCategory(category);
	}

	@GetMapping("/products/price-range")
	public List<Product> getProductsByPriceRange(
	        @RequestParam BigDecimal minPrice,
	        @RequestParam BigDecimal maxPrice) {

	    return ps.getProductsByPriceRange(minPrice, maxPrice);
	}



}
