package com.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.web.model.Category;
import com.web.repository.CategoryRepository;
import com.web.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService<Category,Long>{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category save(Category entity) {
		
		return getRep().save(entity);
	}

	@Override
	public void delete(Long id) {
		
		getRep().deleteById(id);
	}

	@Override
	public Category get(Long id) {
		Optional<Category> obj = getRep().findById(id);
		if(obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<Category> getAll() {
		List<Category> returnList = new ArrayList<>();
		getRep().findAll().forEach(obj -> returnList.add(obj));
		return returnList;
	}

	public CrudRepository<Category,Long> getRep(){
		return categoryRepository;
	}

	@Override
	public Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
		     Sort.by(sortField).descending();
		    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		    return this.categoryRepository.findAll(pageable);
	}
}
