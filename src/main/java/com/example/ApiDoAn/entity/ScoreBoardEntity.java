package com.example.ApiDoAn.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreBoardEntity extends BaseEntity{
	private String name;
	@Type(type = "org.hibernate.type.TextType")
	private String image;
//	@OneToOne
//	@JoinColumn(name = "imageUrl", referencedColumnName = "id")
//	ImageEntity image;
	double score;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private CategoryEntity categoryEntity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public CategoryEntity getCategoryEntity() {
		return categoryEntity;
	}
	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}
	
}
