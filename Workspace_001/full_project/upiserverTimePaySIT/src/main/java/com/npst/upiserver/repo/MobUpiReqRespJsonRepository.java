package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.MobUpiReqRespJson;


@Repository
public interface MobUpiReqRespJsonRepository extends JpaRepository<MobUpiReqRespJson, Long>{
	MobUpiReqRespJson findByIdPk(Long idPk);
    long countByIdPkAndTypeIgnoreCase(long idPk,String type);
	long countByIdPkAndType(long idPk,String type);
}
