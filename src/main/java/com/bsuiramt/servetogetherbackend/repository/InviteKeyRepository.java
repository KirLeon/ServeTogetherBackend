package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.InviteKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteKeyRepository extends JpaRepository<InviteKeyEntity, String> {
}
