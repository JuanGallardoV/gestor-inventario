package com.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	@NotEmpty
	private String name;
	
	@ManyToOne
	@JoinColumn(name="id_category")
	@NotNull
	private Category idCategory;
	
	@Column
	@NotNull
	@Min(value = 0)
	private float price;
	
	@Column
	@NotNull
	@Min(value = 1)
	@Max(value = 100)
	private int profitMargin;
	
	@Column
	@NotNull
	private float salePrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Category idCategory) {
		this.idCategory = idCategory;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(int profitMargin) {
		this.profitMargin = profitMargin;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
}
