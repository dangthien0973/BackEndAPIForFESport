package com.example.ApiDoAn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
	private boolean isNew;
	@Column
	private String sourceOrigin;
	@Column
	private String name;
	@Lob
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
	@JsonIgnore
	@OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
	private List<ImageEntity> ImageEntity;

	

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

	@Override
	public String toString() {
		return "ProductEntity [isNew=" + isNew + ", sourceOrigin=" + sourceOrigin + ", name=" + name + ", descriptions="
				+ descriptions + ", importDate=" + importDate + ", expiryDate=" + expiryDate + ", categoryEntity="
				+ categoryEntity + ", ImageEntity=" + ImageEntity + "]";
	}
	

}
