package com.example.ApiDoAn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "category")
@Entity
public class CategoryEntity extends BaseEntity {
    @JsonIgnore
    @Column
    private String NameCategory;
    @OneToMany(mappedBy = "categoryEntity")
    private Set<ProductEntity> productEntitys ;
	public String getNameCategory() {
		return NameCategory;
	}

	public void setNameCategory(String nameCategory) {
		NameCategory = nameCategory;
	}
	public Set<ProductEntity> getProductEntitys() {
		return productEntitys;
	}
	public void setProductEntitys(Set<ProductEntity> productEntitys) {
		this.productEntitys = productEntitys;
	}

}
