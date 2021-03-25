package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.OrphanDebitCredit;

@Repository
public interface OrphanDebitCreditRepository extends JpaRepository<OrphanDebitCredit,Long>{

}
