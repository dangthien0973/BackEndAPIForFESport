package com.example.ApiDoAn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Column
    private int Price;
    @Column
    private int Price_Sale;
    @Column
    private int amount;
    @Column
    private boolean isNew;
    @Column
    private String sourceOrigin;
    @Column
    private String name;
    @Column
    private String descriptions;
    @Column
    private Date importDate;
    @Column
    private Date expiryDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL)
    private List<ImageEntity> ImageEntity;
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
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public CategoryEntity getCategoryEntity() {
		return categoryEntity;
	}
	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}
	public List<ImageEntity> getImageEntity() {
		return ImageEntity;
	}
	public void setImageEntity(List<ImageEntity> imageEntity) {
		ImageEntity = imageEntity;
	}
    
}
