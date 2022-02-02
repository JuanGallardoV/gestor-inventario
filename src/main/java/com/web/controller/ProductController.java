package com.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.model.Category;
import com.web.model.Product;
import com.web.service.CategoryService;
import com.web.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	//Obtener
//	@RequestMapping("/")
//	public String index(Model model, @Param("keyword") String keyword) {
//		model.addAttribute("list",productService.getAll());
//		model.addAttribute("list", productService.listAll(keyword));
//		model.addAttribute("keyword", keyword);
//		return "index";
//	}
	
	@RequestMapping("/")
    public String viewHomePage(Model model) {			//@Param("keyword") String keyword
        return findPaginated(1, "name", "asc", model);
    }

	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
	    @RequestParam("sortField") String sortField,
	    @RequestParam("sortDir") String sortDir,
	    Model model) {
	    int pageSize = 5;

	    Page <Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
	    List <Product> list = page.getContent();

	    model.addAttribute("currentPage", pageNo);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());

	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

	    model.addAttribute("list", list);
	    return "index";
	}
	
	//Crear/Editar
	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id")Long id, Model model) {
		if(id!=null && id!=0) {
			model.addAttribute("product", productService.get(id));
		}else {
			model.addAttribute("product", new Product());
		}
		List<Category> listCategories = categoryService.getAll();
		model.addAttribute("categories",listCategories);
		return "save";
	}
	//Guardar
	@PostMapping("/save")
	public String save(@Valid Product product, BindingResult result, RedirectAttributes redirect,Model model) {
		List<Category> listCategories = categoryService.getAll();
		model.addAttribute("categories",listCategories);
		if(result.hasErrors()) {
			return "save";
		}
		//Calcular precio Venta
		float round;
		float salePrice;
		float percentage = (float) product.getProfitMargin()/100;
		salePrice = product.getPrice()/(1-percentage);
		//Redondea a dos decimales el precio
		round= (float) (Math.round(salePrice*100.0)/100.0);
		product.setSalePrice(round);
		productService.save(product);
		redirect
		.addFlashAttribute("message","Guardado Correctamente")
		.addFlashAttribute("class","success");
		return "redirect:/";
	}
	//Borrar
	@GetMapping("/delete/{id}")
	//@RequestMapping(value="/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@PathVariable Long id, Model model, RedirectAttributes redirect) {
		productService.delete(id);
		redirect
				.addFlashAttribute("message","Eliminado Correctamente")
				.addFlashAttribute("class","success");
		return "redirect:/";
	}
}
