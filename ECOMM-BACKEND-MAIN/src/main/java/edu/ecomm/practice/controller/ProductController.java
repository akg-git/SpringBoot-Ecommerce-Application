package edu.ecomm.practice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ecomm.practice.entity.Product;
import edu.ecomm.practice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String greeet( HttpServletRequest request) {
		System.out.println("Welcome to Product page.");
		return "Aao kharido!!"+"\n Session ID: "+request.getSession().getId();
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<List<Product>> (products, HttpStatus.OK);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer productId){
		
		Product product = productService.getProductById(productId);
		
		if(product != null)
			return new ResponseEntity<> (product, HttpStatus.OK);
		else
			return new ResponseEntity<> (product, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId){
		
		Product product = productService.getProductById(productId);
		
		byte[] imageFile = product.getImageData();
		String imageType = product.getImageType();
		String imageUrl = product.getImageUrl();
		
//		if(product != null)
//			return ResponseEntity.ok()
//					.contentType(MediaType.valueOf(product.getImageType()))
//					.body(imageFile);
//		else
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image Not found");
		
		try {
			if (imageFile != null && imageFile.length > 0 && imageType != null) {
				return ResponseEntity.ok()
					.contentType(MediaType.valueOf(product.getImageType()))
					.body(imageFile);
			}else if(imageUrl != null && !imageUrl.isBlank()){
				HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(imageUrl));
                return ResponseEntity.status(302).headers(headers).build();
			}
		}catch(Exception e) {
			System.out.println("Image not found.\nError Message: "+e.getMessage());
		}
		return null;
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		
		try {
		Product newProduct = productService.addProduct(product, imageFile);
	
		return new ResponseEntity<> (newProduct, HttpStatus.CREATED);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<> ( HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<String> updateProductById(@PathVariable Integer productId, @RequestPart Product product, @RequestPart MultipartFile imageFile){
		
		try {
			Product updateProduct = productService.updateProductById(productId, product, imageFile);

			if (updateProduct != null)
				return new ResponseEntity<>("Product updated successfully!", HttpStatus.OK);
			else
				return new ResponseEntity<>("Failed to update!", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update product!!\nError Message: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable Integer productId){
		
		try {
			Product deleteProduct = productService.deleteProductById(productId);

			if (deleteProduct != null)
				return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
			else
				return new ResponseEntity<>("Failed to delete!", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete product!!\nError Message: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		System.out.println("Searching with "+keyword);
		List<Product> products = productService.searchProductsWithKeyword(keyword);
		return new ResponseEntity<List<Product>> (products, HttpStatus.OK);
	}
	
	
	
}
