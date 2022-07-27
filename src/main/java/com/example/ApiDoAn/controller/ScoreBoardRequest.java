package com.example.ApiDoAn.controller;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScoreBoardRequest {
	private String name;
	private String image;
	private double score;
	private Long categoryId;
}
