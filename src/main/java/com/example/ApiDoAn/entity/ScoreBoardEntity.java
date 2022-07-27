package com.example.ApiDoAn.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

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
	private CategoryEntity categoryEntity;
}
