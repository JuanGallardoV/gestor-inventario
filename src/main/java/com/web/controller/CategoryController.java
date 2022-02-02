package com.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.model.Category;
import com.web.model.Product;
import com.web.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
//	@RequestMapping("/categories")
//	public String categories(Model model) {
//		model.addAttribute("list",categoryService.getAll());
//		return "categories";
//	}
	
	@RequestMapping("/categories")
    public String viewHomePage(Model model) {			//@Param("keyword") String keyword
        return findPaginated(1, "name", "asc", model);
    }
	
	@GetMapping("/categories/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
	    @RequestParam("sortField") String sortField,
	    @RequestParam("sortDir") String sortDir,
	    Model model) {
	    int pageSize = 5;

	    Page <Category> page = categoryService.findPaginated(pageNo, pageSize, sortField, sortDir);
	    List <Category> list = page.getContent();

	    model.addAttribute("currentPage", pageNo);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());

	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

	    model.addAttribute("list", list);
	    return "categories";
	}
	
	@GetMapping("/saveCat/{id}")
	public String showSave(@PathVariable("id")Long id, Model model) {
		if(id!=null && id!=0) {
			model.addAttribute("category", categoryService.get(id));
			/*boolean newCat = true;
			model.addAttribute("newCat", newCat);*/
		}else {
			model.addAttribute("category", new Category());
			/*boolean newCat = false;
			model.addAttribute("newCat", newCat);*/
		}
		return "saveCat";
	}
	
	@PostMapping("/saveCat")
	public String save(@Valid Category category, BindingResult result, Model model, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return "saveCat";
		}
		categoryService.save(category);
		/*
		 * if(model.getAttribute("newCat").toString() == "true") { redirect
		 * .addFlashAttribute("message","Agregado Correctamente")
		 * .addFlashAttribute("class","success"); }else{ redirect
		 * .addFlashAttribute("message","Editado Correctamente")
		 * .addFlashAttribute("class","success"); }
		 */
		redirect
		.addFlashAttribute("message","Guardado Correctamente")
		.addFlashAttribute("class","success");
		return "redirect:/categories";
	}
	
	@GetMapping("/deleteCat/{id}")
	public String delete(@PathVariable Long id, Model model,RedirectAttributes redirect) {
		try {
			categoryService.delete(id);
			redirect
					.addFlashAttribute("message","Eliminado Correctamente")
					.addFlashAttribute("class","success");
		}catch(Exception e) {
			redirect
					.addFlashAttribute("message","No puede eliminar una categoria que tenga productos asociados")
					.addFlashAttribute("class","info");
		}
		return "redirect:/categories";
	}
}
