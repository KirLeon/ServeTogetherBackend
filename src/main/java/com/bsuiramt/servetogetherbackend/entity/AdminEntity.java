package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@Data
@NoArgsConstructor
public class AdminEntity {
	
	public AdminEntity(AccountInfoEntity info) {
		this.info = info;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	@OneToOne
	private AccountInfoEntity info;
}
