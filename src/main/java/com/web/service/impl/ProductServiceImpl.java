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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.web.model.Product;
import com.web.repository.ProductRepository;
import com.web.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService<Product,Long>{
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product save(Product entity) {
		
		return getRep().save(entity);
	}

	@Override
	public void delete(Long id) {
		
		getRep().deleteById(id);
	}

	@Override
	public Product get(Long id) {
		Optional<Product> obj = getRep().findById(id);
		if(obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<Product> getAll() {
		List<Product> returnList = new ArrayList<>();
		getRep().findAll().forEach(obj -> returnList.add(obj));
		return returnList;
	}

		public CrudRepository<Product,Long> getRep(){
			return productRepository;
		}

		@Override
		public List<Product> listAll(String keyword) {
			if(keyword != null) {
				return productRepository.search(keyword);
			}
			List<Product> returnList = new ArrayList<>();
			getRep().findAll().forEach(obj -> returnList.add(obj));
			return returnList;
		}

		@Override
		public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {	//@Param("keyword") String keyword
			
//			if(keyword!= null) {
//				productRepository.search(keyword);
//			}
			List<Product> returnList = new ArrayList<>();
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			     Sort.by(sortField).descending();
			    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
			    return this.productRepository.findAll(pageable);
		}
		
}
