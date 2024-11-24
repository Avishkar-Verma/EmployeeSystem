package com.interview.employeeSystem.repository;

import com.interview.employeeSystem.repository.entity.TokenDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDetailsRepository extends JpaRepository<TokenDetails, Integer> {
}
