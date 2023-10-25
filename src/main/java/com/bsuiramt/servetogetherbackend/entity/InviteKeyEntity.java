package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invite_keys")
@Data
@NoArgsConstructor
public class InviteKeyEntity {
	
	@Id
	private String code;
	
	private boolean activated;
}
