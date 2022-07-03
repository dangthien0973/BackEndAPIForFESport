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
import com.example.ApiDoAn.request.Detail;
import com.example.ApiDoAn.request.DetailItem;
import com.example.ApiDoAn.request.ProductFilterRequest;
import com.example.ApiDoAn.request.content;
import com.example.ApiDoAn.service.IProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
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
	@JsonIgnore
	ProductRepository productRepository;
	@Autowired
	@JsonIgnore
	CategoryRepository categoryRepository;

	@GetMapping("/product-detail/{productId}")
	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
		Optional<ProductEntity> result = productRepository.findById(productId);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found product", ""));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "product detail", result));

	}
	// pending
	@GetMapping("/toolcrawlData")
	public ResponseEntity<?> toolsaveProduct() throws IOException {
		String uri = "https://tdtt.gov.vn/DesktopModules/EasyDnnGallery/ChameleonGalleryService.ashx?tabid=162&fullscreen=false&mid=5402&portal_id=0&locale=vi-VN&article_id=0&html5_player=1&_=1655312608106";
		RestTemplate restTemplate = new RestTemplate();
		Date dt=new Date();
		String result = restTemplate.getForObject(uri, String.class);
		// lấy kết quả parseto ojbject trả về dùng objectMapper để trả về kết quả
		ObjectMapper objectMapper = new ObjectMapper();
		content data = objectMapper.readValue(result, content.class);
		List<Detail> detail = data.content;
		Detail detailFirst = detail.get(0);
		List<DetailItem> detailItem = detailFirst.items;
		for(DetailItem item :  detailItem) {
			List<ImageEntity> listImage = new ArrayList<ImageEntity>();
			ImageEntity imageEntity = new ImageEntity();
			ProductEntity product = new ProductEntity();
			String urlImge = "https://tdtt.gov.vn/" + item.lightbox_url;
			imageEntity.setUrl(urlImge);
			imageEntity.setCreatedBy("Nguyễn Đăng Thiện");
			imageEntity.setDateCreated(dt);
			listImage.add(imageEntity);
			product.setName(item.title);
			product.setDescriptions(item.title);
			product.setImportDate(dt);
			product.setCreatedBy("nguyễn Đăng Thiện");
			product.setDateCreated(dt);
			product.setImageEntity(listImage);
			productRepository.save(product);
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body("successly!");
	}
	@PostMapping("SearchProduct")
	public ResponseEntity<?> SearchProduct(@RequestParam(required = true) String searchValue,
	   @RequestParam(defaultValue = "0") int pageIndex,  @RequestParam(defaultValue = "10") int pageSize) {
		 Pageable pageable = PageRequest.of(pageIndex,pageSize);
         String param = searchValue;
     	System.err.println(param);
         Page<ProductEntity> result = productRepository.search(param, pageable);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfully!", result));
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
		Page<ProductEntity> lstProduct = productRepository.filterProduct(request.lstCateGory, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(lstProduct);
	}

}
