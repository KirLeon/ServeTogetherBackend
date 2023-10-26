package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerEntity {
	
	public VolunteerEntity(AccountInfoEntity info, VolunteerGroupEntity group, Integer coins) {
		this.info = info;
		this.group = group;
		this.coins = coins;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	@OneToOne
	private AccountInfoEntity info;
	
	@JoinColumn(name = "group_name")
	@ManyToOne
	private VolunteerGroupEntity group;
	
	@Min(value = 0, message = "Coin amount should not be less than zero")
	private Integer coins;
	
}
