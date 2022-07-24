package com.example.ApiDoAn.reponse;

import java.util.List;

import com.example.ApiDoAn.entity.CategoryEntity;
import com.example.ApiDoAn.entity.ImageEntity;

public class ProductResponseUser {
	private Long id;
	private String name;
	private String descriptions;
	private List<ImageResponseUser> ImageEntity;
	private CategoryResponse categoryEntity;
	public ProductResponseUser(Long id, String name, String descriptions, List<ImageResponseUser> imageEntity,
			CategoryResponse categoryEntity) {
		super();
		this.id = id;
		this.name = name;
		this.descriptions = descriptions;
		ImageEntity = imageEntity;
		this.categoryEntity = categoryEntity;
	}
	public ProductResponseUser() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<ImageResponseUser> getImageEntity() {
		return ImageEntity;
	}
	public void setImageEntity(List<ImageResponseUser> imageEntity) {
		ImageEntity = imageEntity;
	}
	public CategoryResponse getCategoryEntity() {
		return categoryEntity;
	}
	public void setCategoryEntity(CategoryResponse categoryEntity) {
		this.categoryEntity = categoryEntity;
	}
	@Override
	public String toString() {
		return "ProductResponseUser [id=" + id + ", name=" + name + ", descriptions=" + descriptions + ", ImageEntity="
				+ ImageEntity + ", categoryEntity=" + categoryEntity + "]";
	}
	
	
}
