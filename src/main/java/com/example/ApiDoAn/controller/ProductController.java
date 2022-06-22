package com.example.ApiDoAn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.ApiDoAn.reponse.ProductResponse;
import com.example.ApiDoAn.reponse.ResponseObject;
import com.example.ApiDoAn.repository.ProductRepository;
import com.example.ApiDoAn.request.content;
import com.example.ApiDoAn.service.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "*", maxAge = 360)
@RequestMapping("api/product")
public class ProductController {
//	@Autowired
//	IProductService iProductService;
//	@Autowired
	ProductRepository productRepository;

//	@GetMapping("/product-detail/{productId}")
//	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
//		ProductResponse result = this.iProductService.findById(productId);
//		if (result == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(),"Not found product", ""));
//
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(new ResponseObject(HttpStatus.OK.value(), "product detail", result));
//
//	}

//	@GetMapping("/ShowAndsearch")
//	public ResponseEntity<?> showAndsearchProductEntity(@RequestParam(required = true) String searchValue,
//			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
//
//		Pageable pageable = PageRequest.of(pageIndex, pageSize);
//		Map<String, Object> result = this.iProductService.showAndSearchProduct(searchValue, pageable);
//		if (result.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "no product in db", ""));
//		}
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(new ResponseObject(HttpStatus.OK.value(), "successly!", result));
//	}
	@GetMapping("/toolcrawlData")
	public ResponseEntity<?> toolsaveProduct() throws IOException {
		 String uri = "https://tdtt.gov.vn/DesktopModules/EasyDnnGallery/ChameleonGalleryService.ashx?tabid=162&fullscreen=false&mid=5402&portal_id=0&locale=vi-VN&article_id=0&html5_player=1&_=1655312608106";
		RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    // lấy kết quả parseto ojbject trả về dùng objectMapper để trả về kết quả
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    content data = objectMapper.readValue(result, content.class);
	    System.err.println(data.toString());
		return ResponseEntity.status(HttpStatus.OK)
				.body("successly!");
	}
	
	
}
