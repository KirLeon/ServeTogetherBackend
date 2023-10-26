package com.bsuiramt.servetogetherbackend.entity;

import com.bsuiramt.servetogetherbackend.model.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoEntity {
	
	public AccountInfoEntity(UserRole role, String username, String password, String phoneNumber) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String phoneNumber;
	
	private String registryToken;
}
