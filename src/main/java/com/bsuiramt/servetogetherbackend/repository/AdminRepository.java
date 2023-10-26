package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {
	public Optional<AdminEntity> findAdminEntityByInfoId(Long info_id);
}
