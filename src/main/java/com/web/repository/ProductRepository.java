package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
	//Filtro
		@Query("SELECT p FROM Product p WHERE CONCAT(p.id,' ',p.name, ' ', p.price, ' ', p.profitMargin, ' ', p.salePrice, ' ', p.idCategory.name) LIKE %?1%")
		public List<Product>search(String keyword);
}
