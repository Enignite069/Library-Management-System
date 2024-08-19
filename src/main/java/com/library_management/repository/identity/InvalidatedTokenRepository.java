package com.library_management.repository.identity;

import com.library_management.entity.identity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}

