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

import com.example.ApiDoAn.entity.CategoryEntity;
import com.example.ApiDoAn.entity.ImageEntity;
import com.example.ApiDoAn.entity.ProductEntity;
import com.example.ApiDoAn.reponse.ProductResponse;
import com.example.ApiDoAn.reponse.ResponseObject;
import com.example.ApiDoAn.repository.CategoryRepository;
import com.example.ApiDoAn.repository.ProductRepository;
import com.example.ApiDoAn.request.ProductFilterRequest;
import com.example.ApiDoAn.request.content;
import com.example.ApiDoAn.service.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController
//@CrossOrigin(origins = "*", maxAge = 360)
@RequestMapping("api/product")
public class ProductController {
//	@Autowired
//	IProductService iProductService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/product-detail/{productId}")
	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
		Optional<ProductEntity> result = productRepository.findById(productId);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(),"Not found product", ""));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "product detail", result));

	}
	// Thiện filter Product
	@PostMapping("filterProduct")
	public ResponseEntity<?> filterProduct(@Valid @RequestBody ProductFilterRequest request) {
		// khởi tạo đối tượng
		ArrayList<Long> listCateGoryProduct = new ArrayList<Long>();
		int pageIndex = request.pageIndex;
		int pageSize = request.pageSize;
		// nếu không truyền category thì cũng mặc định lấy hết category lên để xét
		if (request.lstCateGory == null) {
			// lấy hết category 3 từ database lên

			List<CategoryEntity> listCategory = categoryRepository.findAll();
			for (CategoryEntity categoryEntity : listCategory) {
				;
				System.err.println(categoryEntity.getId());
				listCateGoryProduct.add(categoryEntity.getId());
			}
			request.lstCateGory = listCateGoryProduct;
		}

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ProductEntity> lstProduct = productRepository.filterProduct(request.lstCateGory, request.keyWord, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(lstProduct);
	}
	// pending
	@GetMapping("/toolcrawlData")
	public ResponseEntity<?> toolsaveProduct() throws IOException {
		String uri = "https://tdtt.gov.vn/DesktopModules/EasyDnnGallery/ChameleonGalleryService.ashx?tabid=162&fullscreen=false&mid=5402&portal_id=0&locale=vi-VN&article_id=0&html5_player=1&_=1655312608106";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		// lấy kết quả parseto ojbject trả về dùng objectMapper để trả về kết quả

		ObjectMapper objectMapper = new ObjectMapper();
		content data = objectMapper.readValue(result, content.class);
		System.err.println(data.toString());
		return ResponseEntity.status(HttpStatus.OK).body("successly!");
	}

	// tool crawl data đơn giản để test thôi !

	@PostMapping("/toolsaveProduct")
	public ResponseEntity<?> saveProduct() {
		
		
		Date dt = new Date(2022, 6, 4);
		for (int i = 0; i < 10; i++) {
			CategoryEntity category = new CategoryEntity();
			
			List<ImageEntity> list = new ArrayList<ImageEntity>();
			ImageEntity imageEntity = new ImageEntity();
			String url = "https://tdtt.gov.vn//Portals/0/EasyGalleryImages/2/1320/IMG_2888.JPG";
			imageEntity.setUrl(url);
			list.add(imageEntity);
			ProductEntity etitry = new ProductEntity();
			etitry.setAmount(2000);
			etitry.setName("Tin tức thể thao test");
			
			etitry.setPrice(20000);
			etitry.setPrice_Sale(200);

			etitry.setImportDate(dt);
			etitry.setImageEntity(list);
			productRepository.save(etitry);
		}
		for (int i = 0; i < 10; i++) {
			List<ImageEntity> list = new ArrayList<ImageEntity>();
			ImageEntity imageEntity = new ImageEntity();
			String url = "https://tdtt.gov.vn//Portals/0/EasyGalleryImages/2/1320/IMG_3382.JPG";
			imageEntity.setUrl(url);
			list.add(imageEntity);
			ProductEntity etitry = new ProductEntity();
			etitry.setAmount(2000);
			etitry.setName("Tin tức thể thao test 2");
			
			etitry.setPrice(20000);
			etitry.setPrice_Sale(200);
			etitry.setImportDate(dt);
			etitry.setImageEntity(list);
			productRepository.save(etitry);
		}

		return ResponseEntity.status(HttpStatus.OK).body("successly!");

	}
}
