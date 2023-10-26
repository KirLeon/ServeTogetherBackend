package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long> {
	boolean existsAccountInfoEntityByUsername(String username);
	
	boolean existsAccountInfoEntityByPhoneNumber(String phoneNumber);
	
	Optional<AccountInfoEntity> findAccountInfoEntityByUsername(String username);
	
	Optional<AccountInfoEntity> findAccountInfoEntityByPhoneNumber(String phoneNumber);
}
