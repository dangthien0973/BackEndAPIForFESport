package com.example.ApiDoAn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class CategoryEntity extends BaseEntity {
    @JsonIgnore
    @Column
    private String NameCategory;
    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY)
    private Set<ProductEntity> productEntitys;
    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY)
    private Set<ScoreBoardEntity> scoreBoardEntitys;
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
