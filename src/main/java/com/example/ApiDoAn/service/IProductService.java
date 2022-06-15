package com.example.ApiDoAn.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ApiDoAn.reponse.ProductResponse;

import java.util.List;
import java.util.Map;

public interface IProductService {

    ProductResponse findById(Long productId);

    Map<String,Object> showAndSearchProduct(String searchValue, Pageable pageable);
}
