package com.example.ApiDoAn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity {
    @Column
    private String url;
    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    ProductEntity productEntity;
}
