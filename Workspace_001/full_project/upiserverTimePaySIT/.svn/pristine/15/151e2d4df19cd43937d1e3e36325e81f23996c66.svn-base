package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.MobMandateReqRespJsonIdEntity;

public interface MobMandateReqRespJsonIdRepo extends JpaRepository<MobMandateReqRespJsonIdEntity, Long> {

	MobMandateReqRespJsonIdEntity findById(long idPk);

	List<MobMandateReqRespJsonIdEntity> findByMsgid(String msgId);
}
