package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.BlockedByCustomer;

@Repository
public interface BlockedByCustomerRepository extends JpaRepository<BlockedByCustomer,Long> {
	int countByRegidAndBlockedvpa(int regid,String blockedvpa);
	long countByRegidAndBlockedvpaAndStatus(int regId,String vpa,int status);
}
