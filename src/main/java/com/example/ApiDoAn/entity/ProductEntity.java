package com.example.ApiDoAn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

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
	private String sourceOrigin;
	@Column
	@Type(type = "org.hibernate.type.TextType")
	private String name;
	@Column
	@Type(type = "org.hibernate.type.TextType")
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
	@OneToMany(mappedBy = "productEntity",fetch = FetchType.EAGER)
	private List<ImageEntity> ImageEntity;

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
		return "ProductEntity [isNew="  + ", sourceOrigin=" + sourceOrigin + ", name=" + name + ", descriptions="
				+ descriptions + ", importDate=" + importDate + ", expiryDate=" + expiryDate + ", categoryEntity="
				+ categoryEntity + ", ImageEntity=" + ImageEntity + "]";
	}
	

}
