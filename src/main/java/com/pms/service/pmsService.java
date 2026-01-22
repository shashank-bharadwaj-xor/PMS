package com.pms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.dao.pmsDao;
import com.pms.model.Product;

@Service
public class pmsService 
{
	@Autowired
	pmsDao pd;

	public List<Product> insertData(List<Product> product) {
		// TODO Auto-generated method stub
		return pd.insertData(product);
	}

	public List<Product> getData() {
		// TODO Auto-generated method stub
		return pd.getData();
	}

	public Product getDataById(Long id) {
		// TODO Auto-generated method stub
		return pd.getDataById(id);
	}
	
	public Product updateProduct(Long id, Product updatedProduct) 
	{
	    return pd.updateProduct(id, updatedProduct);
	}
	public void deleteProduct(Long id) 
	{
	    pd.deleteProduct(id);
	}

	public List<Product> getDataByCategory(String category) {
	    return pd.getDataByCategory(category);
	}
	public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
	    return pd.getProductsByPriceRange(minPrice, maxPrice);
	}



}
