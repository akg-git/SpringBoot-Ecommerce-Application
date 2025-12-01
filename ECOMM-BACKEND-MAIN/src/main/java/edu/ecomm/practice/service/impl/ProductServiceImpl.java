package edu.ecomm.practice.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.ecomm.practice.entity.Product;
import edu.ecomm.practice.repository.ProductRepository;
import edu.ecomm.practice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Integer productId) {
		return productRepository.findById(productId).orElse(null);
	}

	@Override
	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		
		return productRepository.save(product);
	}

	@Override
	public Product updateProductById(Integer productId, Product product, MultipartFile imageFile) throws IOException {

//		Product updateProduct = getProductById(productId);
		Product updateProduct = productRepository.findById(productId).orElseThrow();
		
		if (product.getProductName() != null) {
	        updateProduct.setProductName(product.getProductName());
	    }
	    if (product.getDesc() != null) {
	        updateProduct.setDesc(product.getDesc());
	    }
	    if (product.getPrice() != 0.0f) {
	        updateProduct.setPrice(product.getPrice());
	    }
	    if (product.getBrand() != null) {
	        updateProduct.setBrand(product.getBrand());
	    }
	    if (product.getCategory() != null) {
	        updateProduct.setCategory(product.getCategory());
	    }
	    if (product.getQuantity() != 0) {
	        updateProduct.setQuantity(product.getQuantity());
	    }
	    if (product.getReleaseDate() != null) {
	        updateProduct.setReleaseDate(product.getReleaseDate());
	    }
	    
	    if (imageFile != null && !imageFile.isEmpty()) {
	        updateProduct.setImageData(imageFile.getBytes());
	        updateProduct.setImageName(imageFile.getOriginalFilename());
	        updateProduct.setImageType(imageFile.getContentType());
	    }
	    
		return productRepository.save(updateProduct);
		
	}

	@Override
	public Product deleteProductById(Integer productId) {
		
		Product deleteProduct = getProductById(productId);
		deleteProduct.setActive(false);
//		productRepository.save(deleteProduct);
		productRepository.softDeleteById(productId);
		
		return deleteProduct;

	}

	@Override
	public List<Product> searchProductsWithKeyword(String keyword) {
		List<Product> searchedProducts = productRepository.searchProductsWithKeyword(keyword);
		return searchedProducts;
	}
	
	

}
