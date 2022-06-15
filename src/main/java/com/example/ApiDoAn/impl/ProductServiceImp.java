/*
 * package com.example.ApiDoAn.impl;
 * 
 * import org.modelmapper.ModelMapper; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.Pageable; import
 * org.springframework.stereotype.Service;
 * 
 * import com.example.ApiDoAn.entity.CategoryEntity; import
 * com.example.ApiDoAn.entity.ProductEntity; import
 * com.example.ApiDoAn.reponse.ProductResponse; import
 * com.example.ApiDoAn.repository.CategoryRepository; import
 * com.example.ApiDoAn.repository.ProductRepository; import
 * com.example.ApiDoAn.service.IProductService;
 * 
 * import java.util.ArrayList; import java.util.HashMap; import java.util.List;
 * import java.util.Map;
 * 
 * @Service public class ProductServiceImp implements IProductService {
 * 
 * @Autowired ProductRepository productRepository;
 * 
 * @Autowired CategoryRepository categoryRepository;
 * 
 * @Autowired ModelMapper mapper;
 * 
 * @Override public ProductResponse findById(Long productId) { ProductEntity
 * productEntity = productRepository.findById(productId).get(); CategoryEntity
 * categoryEntity = categoryRepository.findByProductEntitys(productEntity);
 * CategoryRepository categoryResponse =
 * this.mapper.map(categoryEntity,CategoryRepository.class); ProductResponse
 * productResponse = this.mapper.map(productEntity,ProductResponse.class);
 * productResponse.setCategoryResponse(categoryResponse); return productResponse
 * ; }
 * 
 * @Override public Map<String ,Object> showAndSearchProduct(String searchValue,
 * Pageable pageable) {
 * 
 * Page<ProductEntity> pageTuts; if(searchValue.equals("all")) { pageTuts =
 * this.productRepository.findAll(pageable); }else { pageTuts =
 * this.productRepository.findByNameContainingIgnoreCase(searchValue, pageable);
 * } List<ProductEntity> productEntityList = pageTuts.getContent();
 * List<ProductResponse> ProductResponse =
 * this.covertProductEntityToResponse(productEntityList); Map<String,Object>
 * result = new HashMap<>(); result.put("products",ProductResponse);
 * result.put("curerentPage",pageTuts.getNumber());
 * result.put("totalitems",pageTuts.getTotalElements());
 * result.put("totalPage",pageTuts.getTotalPages()); return result ; } public
 * List<ProductResponse> covertProductEntityToResponse(List<ProductEntity>
 * productDetailEntities){ List<ProductResponse> responseList = new
 * ArrayList<>(); for (ProductEntity productEntity :productDetailEntities) {
 * 
 * responseList.add(this.mapper.map(productEntity,ProductResponse.class));
 * System.out.println(productDetailEntities);
 * 
 * } return responseList; }
 * 
 * 
 * 
 * }
 */