package edu.ecomm.practice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.ecomm.practice.entity.Product;

@Service
public interface ProductService {
	
	List<Product> getAllProducts();

	Product getProductById(Integer productId);
	
	Product addProduct(Product product, MultipartFile imageFile) throws IOException;
	
//	Product updateProduct(Product product);

	Product updateProductById(Integer productId, Product product, MultipartFile imageFile) throws IOException;

	Product deleteProductById(Integer productId);

	List<Product> searchProductsWithKeyword( String keyword);

}
