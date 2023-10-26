package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "market_items")
@Data
@NoArgsConstructor
public class MarketItemEntity {
	
	public MarketItemEntity(String title, Integer price, String img_path) {
		this.title = title;
		this.price = price;
		this.img_path = img_path;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Min(value = 0, message = "Price should not be less than zero")
	private Integer price;
	
	private String img_path;
}
