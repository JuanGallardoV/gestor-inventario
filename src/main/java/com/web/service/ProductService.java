package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.model.Product;

public interface ProductService<Product, Long> {
	//Guardar
	Product save(Product entity);
	//Borrar
	void delete(Long id);
	//Obtener un producto
	Product get(Long id);
	//Listar todos los productos
	List<Product>getAll();
	
	List<Product>listAll(String keyword);
	
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);		//@Param("keyword") String keyword
}