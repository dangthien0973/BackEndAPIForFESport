package com.example.ApiDoAn.reponse;


import com.example.ApiDoAn.repository.CategoryRepository;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private int Price;
    private int Price_Sale;
    private int amount;
    private boolean isNew;
    private String sourceOrigin;
    private String name;
    private String descriptions;
    private CategoryResponse categoryResponse;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getPrice_Sale() {
		return Price_Sale;
	}
	public void setPrice_Sale(int price_Sale) {
		Price_Sale = price_Sale;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public String getSourceOrigin() {
		return sourceOrigin;
	}
	public void setSourceOrigin(String sourceOrigin) {
		this.sourceOrigin = sourceOrigin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public CategoryResponse getCategoryResponse() {
		return categoryResponse;
	}


}
