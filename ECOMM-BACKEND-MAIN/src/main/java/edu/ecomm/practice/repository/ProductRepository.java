package edu.ecomm.practice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ecomm.practice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	@Transactional
    @Modifying
    @Query("UPDATE Product p SET p.isActive = false WHERE p.productId = :id")
    void softDeleteById(@Param("id") Integer id);
	
	@Query("SELECT p FROM Product p WHERE "
			+ "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR "
			+ "LOWER(p.desc) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR "
			+ "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR "
			+ "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword ,'%')) ")
	List<Product> searchProductsWithKeyword(String keyword);

}
