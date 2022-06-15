package com.example.ApiDoAn.request;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private int Price;
    private int Price_Sale;
    private int amount;
    private boolean isNew;
    private String sourceOrigin;
    private String name;
    private String descriptions;
    private CategoryResponse categoryResponse;

}
