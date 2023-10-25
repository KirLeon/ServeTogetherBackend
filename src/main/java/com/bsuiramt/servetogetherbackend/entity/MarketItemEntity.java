package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "market")
@NoArgsConstructor
public class MarketItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Long price;
	private String img_path;
}
