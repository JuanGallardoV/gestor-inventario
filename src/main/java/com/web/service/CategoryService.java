package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CategoryService<Category, Long> {
	
	Category save(Category entity);
	
	void delete(Long id);
	
	Category get(Long id);
	
	List<Category>getAll();
	
	Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
