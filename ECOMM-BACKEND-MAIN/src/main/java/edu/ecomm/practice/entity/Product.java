package edu.ecomm.practice.entity;

import java.sql.Date;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@Where(clause = "is_active=true")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	@Column(name = "descriptions")
	private String desc;
	private String brand;
	private float price;
	private String category;
	private boolean availability;
	private int quantity;
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date releaseDate;
	
	private String imageName;
	private String imageType;
	@Lob
	private byte[] imageData;
	@Lob
    @Column(name = "image_url", columnDefinition = "TEXT")
	private String imageUrl;
	
	private boolean isActive = true;
	
	//constructors
	public Product() {
		super();
	}
	
	public Product(int productId, String productName, String desc, String brand, float price, String category,
			boolean availability, int quantity, Date releaseDate) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.desc = desc;
		this.brand = brand;
		this.price = price;
		this.category = category;
		this.availability = availability;
		this.quantity = quantity;
		this.releaseDate = releaseDate;
	}
	
	// getters and setters
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public boolean isAvailability() {
		return availability;
	}


	public void setAvailability(boolean availability) {
		this.availability = availability;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// overridden toString()
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", desc=" + desc + ", brand="
				+ brand + ", price=" + price + ", category=" + category + ", availability=" + availability
				+ ", quantity=" + quantity + ", releaseDate=" + releaseDate + ", imageName=" + imageName
				+ ", imageType=" + imageType + "]";
	}

	

}
