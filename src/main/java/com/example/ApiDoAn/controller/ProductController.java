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
import com.example.ApiDoAn.repository.UserRepository;
import com.example.ApiDoAn.request.Detail;
import com.example.ApiDoAn.request.DetailItem;
import com.example.ApiDoAn.request.Image;
import com.example.ApiDoAn.request.ProductFilterRequest;
import com.example.ApiDoAn.request.ProductRequest;
import com.example.ApiDoAn.request.content;
import com.example.ApiDoAn.service.IProductService;
import com.example.ApiDoAn.until.SendEmailUtils;
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

import javax.mail.MessagingException;
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
	@Autowired
	private SendEmailUtils sendEmailUtils;
	 @Autowired
	UserRepository userRepository;
	 @Autowired
	 ImageRepository imageRepository;
	@GetMapping("/product-detail/{productId}")
	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
		Optional<ProductEntity> result = productRepository.findById(productId);
//		hiep
		ProductResponseUser productResponse= mapper.map(result, ProductResponseUser.class);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found product", ""));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "product detail", result));

	}
	// pending
	@GetMapping("/toolcrawlData")
	public ResponseEntity<?> toolsaveProduct(@RequestParam(required = true) String uri) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		Date dt = new Date();
		System.out.println(uri);
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
			// phần image
			String urlbase64 = "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 ";
			ImageEntity imageEntity1= new ImageEntity();
			imageEntity.setUrl(urlbase64);
			imageEntity.setProductEntity(product);
			listImage.add(imageEntity);
			imageReposiotry.save(imageEntity1);
		}

		return ResponseEntity.status(HttpStatus.OK).body("successly!");
	}

	@PostMapping("SearchProduct")
	public ResponseEntity<?> SearchProduct(@RequestParam(required = false) String searchValue,
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		String param = searchValue;
		System.out.println("searchProduct");
		System.out.println(searchValue.length());
		Page<ProductEntity> result =null;
		if(searchValue.length()>1) {
			result = productRepository.search(param, pageable);
		}else {
			result = null;
		}
		
	
	
		
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
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest request) throws MessagingException, IOException {
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
		 String result =registerEmail(product.getName(),product.getId());
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
		List<ImageEntity> listImage=imageRepository.listImageByProduct(id);	

		for (ImageEntity image : listImage) {
			imageRepository.deleteById(image.getId());
		}
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
//		ScoreBoardEntity sc=scoreBoardRepo.findById(id).get();
		List<ScoreBoardEntity> result=scoreBoardRepo.findByCategoryEntityId(id);
		List<ScoreBoardResponse> result2=new ArrayList<ScoreBoardResponse>();
		List<ScoreBoardResponse> result3=new ArrayList<ScoreBoardResponse>();
		for (ScoreBoardEntity scoreBoardEntity : result) 
		{
			result2.add(mapper.map(scoreBoardEntity, ScoreBoardResponse.class));
		}
		for (int i = 0; i <=1; i++) {
			result3.add(mapper.map(result2.get(i), ScoreBoardResponse.class));
		}
		
		System.out.println(result3.size());
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!",result));
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
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!","Them thanh cong"));
	}
	@PostMapping("getAllProducttoAdmin")
	public ResponseEntity<?> getAllProducttoAdmin(@RequestParam(value = "pageIndex") int pageIndex) {
		int pageIndextoCheck =0;
		pageIndextoCheck =pageIndex ;
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ProductEntity> result = productRepository.findAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successfully!", result));
		
	}
	 public String registerEmail(String title, long id) throws MessagingException, IOException {
	      // lấy tất cả user 
		        long idUser = 10;
	    		UserEntity user =  userRepository.findByUserID(idUser);
	            sendEmailUtils.sendEmailWithAttachment(user, 123123 ,"http://localhost:3000/"+id,title );
	    	
	        return "Đăng kí thành công";
	    }

}
