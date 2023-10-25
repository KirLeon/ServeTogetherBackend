package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@Data
@NoArgsConstructor
public class AdminEntity {
	
	@Id
	@Column(name = "UID")
	private Long uid;
	
	@OneToOne
	private AccountInfoEntity info;
}
