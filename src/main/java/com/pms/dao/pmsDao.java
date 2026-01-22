package com.pms.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pms.model.Product;
import com.pms.repository.pmsRepository;

@Repository
public class pmsDao 
{
	@Autowired
	pmsRepository pm;

	public List<Product> insertData(List<Product> product) {
		// TODO Auto-generated method stub
		return  pm.saveAll(product);
	}

	public List<Product> getData() {
		// TODO Auto-generated method stub
		return pm.findAll();	
	}

	public Product getDataById(Long id) {
		// TODO Auto-generated method stub
		return pm.findById(id).orElseThrow(()-> new RuntimeException("404 not found"));
	}
	public Product updateProduct(Long id, Product updatedProduct) {
	    Product existing = pm.findById(id).orElseThrow(() -> new RuntimeException("404 Not Found"));
	    
	    existing.setName(updatedProduct.getName());
	    existing.setDescription(updatedProduct.getDescription());
	    existing.setCategory(updatedProduct.getCategory());
	    existing.setPrice(updatedProduct.getPrice());
	    existing.setStockQuantity(updatedProduct.getStockQuantity());

	    return pm.save(existing);
	}
	
	public void deleteProduct(Long id)
	{
		   Product existing = pm.findById(id).orElseThrow(() -> new RuntimeException("404 Not Found"));

		    pm.delete(existing);
	}


	public List<Product> getDataByCategory(String category) {
		return pm.findAll().stream().filter(p -> p.getCategory() != null && p.getCategory().equalsIgnoreCase(category))
             .collect(Collectors.toList()) ;
	}

	public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
	    return pm.findAll()
	             .stream()
	             .filter(p -> p.getPrice() != null &&
	                          p.getPrice().compareTo(minPrice) >= 0 &&
	                          p.getPrice().compareTo(maxPrice) <= 0)
	             .collect(Collectors.toList());
	}

}
