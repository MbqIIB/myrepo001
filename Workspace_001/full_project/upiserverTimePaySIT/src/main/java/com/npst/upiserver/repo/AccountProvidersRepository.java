package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.AccountProvidersEntity;

public interface AccountProvidersRepository extends JpaRepository<AccountProvidersEntity, Long>{
	AccountProvidersEntity findFirstByOrderByIdaccountprovidersDesc();
}
