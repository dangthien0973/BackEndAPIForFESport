package com.example.ApiDoAn.controller;

import org.modelmapper.ModelMapper;
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
import com.example.ApiDoAn.entity.ScoreBoardEntity;
import com.example.ApiDoAn.entity.ScoreBoardResponse;
import com.example.ApiDoAn.entity.UserEntity;
import com.example.ApiDoAn.reponse.ProductResponse;
import com.example.ApiDoAn.reponse.ProductResponseUser;
import com.example.ApiDoAn.reponse.ResponseObject;
import com.example.ApiDoAn.repository.CategoryRepository;
import com.example.ApiDoAn.repository.ImageRepository;
import com.example.ApiDoAn.repository.ProductRepository;
import com.example.ApiDoAn.repository.ScoreBoardRepository;
import com.example.ApiDoAn.request.Detail;
import com.example.ApiDoAn.request.DetailItem;
import com.example.ApiDoAn.request.Image;
import com.example.ApiDoAn.request.ProductFilterRequest;
import com.example.ApiDoAn.request.ProductRequest;
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
	@Autowired
	@JsonIgnore
	ImageRepository imageReposiotry;
	@Autowired
	ScoreBoardRepository scoreBoardRepo;
	@Autowired
	ModelMapper mapper;

	@GetMapping("/product-detail/{productId}")
	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
		Optional<ProductEntity> result = productRepository.findById(productId);
//		hiep
		ProductResponseUser productResponse=mapper.map(result, ProductResponseUser.class);
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
		Date dt = new Date();
		String result = restTemplate.getForObject(uri, String.class);
		// lấy kết quả parseto ojbject trả về dùng objectMapper để trả về kết quả
		ObjectMapper objectMapper = new ObjectMapper();
		content data = objectMapper.readValue(result, content.class);
		List<Detail> detail = data.content;
		Detail detailFirst = detail.get(0);
		List<DetailItem> detailItem = detailFirst.items;
		for (DetailItem item : detailItem) {
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
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		String param = searchValue;
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

				listCateGoryProduct.add(categoryEntity.getId());
			}
			request.lstCateGory = listCateGoryProduct;
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ProductEntity> lstProduct = productRepository.filterProduct(request.lstCateGory, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(lstProduct);
	}

	// them san pham
	@PostMapping("addProduct")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest request) {
		// khởi tạo đối tượng

		Date dt = new Date();
		List<ImageEntity> listImage = new ArrayList<ImageEntity>();
		ProductEntity product = new ProductEntity();
		product.setName(request.name);
		System.out.println(request.descriptions);
		product.setDescriptions(request.descriptions);
		product.setImportDate(dt);
		product.setCreatedBy("Admin");
		product.setDateCreated(dt);
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		for (CategoryEntity categoryEntity : listCategory) {
			if (categoryEntity.getId() == request.categoryId) {
				product.setCategoryEntity(categoryEntity);
			}

		}
		productRepository.save(product);
		for (Image image : request.ImageEntity) {
			ImageEntity imageEntity = new ImageEntity();
			imageEntity.setUrl(image.url);
			imageEntity.setProductEntity(product);
			listImage.add(imageEntity);
			imageReposiotry.save(imageEntity);
			
		}
		product.setImageEntity(listImage);
		productRepository.save(product);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!","Them thanh cong"));
	}
	// lấy hết sản phẩm ra xử lí
    // làm cho admin
	@PostMapping("getAllProduct")
	public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "8") int pageSize) {
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		 Page<ProductEntity> pageTuts;
		  pageTuts = this.productRepository.findAll(pageable);
	
		 List<ProductResponseUser> listProductResponseUser=new ArrayList<ProductResponseUser>();
		 List<ProductEntity> productEntityList = pageTuts.getContent();
		for (ProductEntity productEntity : productEntityList) {
			listProductResponseUser.add(mapper.map(productEntity, ProductResponseUser.class));
		}
	     Map<String, Object> result2 = new HashMap<>();
	        result2.put("products", listProductResponseUser);
	        result2.put("curerentPage", pageTuts.getNumber());
	        result2.put("totalitems", pageTuts.getTotalElements());
	        result2.put("totalPage", pageTuts.getTotalPages());
	        result2.put("itemInPages", pageTuts.getNumberOfElements());
	        if (listProductResponseUser.get(0).getImageEntity().size()>0) {
				System.out.println("chuan cmnr");
				listProductResponseUser.get(0).getImageEntity().size();
			}else {
				System.out.println("vc");
				listProductResponseUser.get(0).getImageEntity().size();
			}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfully!", result2));
	}
//	cuaHiep
	@PostMapping("getAllProductByCategory")
	public ResponseEntity<?> getAllProductByCategory(@RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "8") int pageSize, @RequestParam(defaultValue = "1") String searchValue) {
	     Map<String, Object> result2=null;
			System.out.println(searchValue);
				Pageable pageable = PageRequest.of(pageIndex, pageSize);
				 Page<ProductEntity> pageTuts=null;
				 if(searchValue.equalsIgnoreCase("1")) {
					 pageTuts = this.productRepository.findAll(pageable);

					 
				 }else if (searchValue.equalsIgnoreCase("2")) {
					 System.out.println("bong da");
					 pageTuts=productRepository.findByCategoryEntityId((long) 1, pageable);
					
				}else if (searchValue.equalsIgnoreCase("3")) {
					 System.out.println("bong ro");
					 pageTuts=productRepository.findByCategoryEntityId((long) 2, pageable);
				}else if (searchValue.equalsIgnoreCase("4")) {
					System.out.println("tennis");
					 pageTuts=productRepository.findByCategoryEntityId((long) 3, pageable);
				}else if (searchValue.equalsIgnoreCase("5")) {
					 pageTuts=productRepository.findByCategoryEntityId((long) 4, pageable);
				}
				
				 
			
				 List<ProductResponseUser> listProductResponseUser=new ArrayList<ProductResponseUser>();
				 List<ProductEntity> productEntityList = pageTuts.getContent();
				for (ProductEntity productEntity : productEntityList) {
					listProductResponseUser.add(mapper.map(productEntity, ProductResponseUser.class));
				}
			  result2 = new HashMap<>();
			        result2.put("products", listProductResponseUser);
			        result2.put("curerentPage", pageTuts.getNumber());
			        result2.put("totalitems", pageTuts.getTotalElements());
			        result2.put("totalPage", pageTuts.getTotalPages());
			        result2.put("itemInPages", pageTuts.getNumberOfElements());
				 
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfully!", result2));
	}
	@PostMapping("DeleteProduct")
	public ResponseEntity<?> DeleteProduct(@RequestParam(value = "id") long id) {
		int pageIndextoCheck =0;
		productRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfully!","Xóa thành công"));
	}
	@PostMapping("testapi")
	public ResponseEntity<?> tespApi() {
//		Pageable pageable = PageRequest.of(0, 8);
//		Page<ProductEntity> page=productRepository.findByCategoryEntityId((long) 2, pageable);
		List<ProductEntity> listProduct=productRepository.getRecentNew();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!",listProduct));
	}
	@PostMapping("recentPost")
	public ResponseEntity<?> recentPost() {
		List<ProductEntity> listProduct=productRepository.getRecentNew();	
		 List<ProductResponseUser> listProductResponseUser=new ArrayList<ProductResponseUser>();
		for (ProductEntity productEntity : listProduct) {
			listProductResponseUser.add(mapper.map(productEntity, ProductResponseUser.class));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!",listProductResponseUser));
	}
	
//	addScoreBoardEntity
	@PostMapping("addScoreBoard")
	public ResponseEntity<?> addScoreBoard(@RequestBody ScoreBoardRequest sc){
		ScoreBoardEntity scoreBoard=new ScoreBoardEntity();
		scoreBoard.setName(sc.getName());
		scoreBoard.setImage(sc.getImage());
//		ImageEntity image=new ImageEntity();
//		image.setUrl(sc.getImage());
//		imageReposiotry.save(image);
//		scoreBoard.setImage(image);
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		for (CategoryEntity categoryEntity : listCategory) {
			if (categoryEntity.getId() == sc.getCategoryId()) {
				scoreBoard.setCategoryEntity(categoryEntity);
			}

		}
		scoreBoardRepo.save(scoreBoard);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!",scoreBoard.getName()));
	}
	
	@PostMapping("showScoreBoard")
	public ResponseEntity<?> showScoreBoard(@RequestParam("id") Long id){
	
		System.out.println(id);
		ScoreBoardEntity sc=scoreBoardRepo.findById(id).get();
		ScoreBoardResponse sc2=new ScoreBoardResponse();
		sc2.setName(sc.getName());
		sc2.setImage(sc.getImage());
		sc2.setScore(sc.getScore());
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!",sc2));
	}
	
	@PostMapping("editProduct")
	public ResponseEntity<?> editProduct(@Valid @RequestBody ProductRequest request) {
		// khởi tạo đối tượng

		Date dt = new Date();
		List<ImageEntity> listImage = new ArrayList<ImageEntity>();
		ProductEntity product = new ProductEntity();
		product.setId(request.id);
		product.setName(request.name);
		product.setDescriptions(request.descriptions);
		product.setImportDate(dt);
		product.setCreatedBy("Admin");
		product.setDateCreated(dt);
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		for (CategoryEntity categoryEntity : listCategory) {
			if (categoryEntity.getId() == request.categoryId) {
				product.setCategoryEntity(categoryEntity);
			}

		}
		productRepository.save(product);
		/*
		 * for (Image image : request.ImageEntity) { ImageEntity imageEntity = new
		 * ImageEntity();
		 * 
		 * imageEntity.setUrl(image.url); imageEntity.setProductEntity(product);
		 * imageReposiotry.save(imageEntity); }
		 */
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!","Them thanh cong"));
	}

}
