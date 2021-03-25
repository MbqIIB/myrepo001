package com.npst.upiserver.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.MobReqRespJsonId;



@Repository
public interface MobReqRespJsonIdRepository extends JpaRepository<MobReqRespJsonId,Long> {
	MobReqRespJsonId findDistinctByMsgid(final String msgid);
	MobReqRespJsonId findDistinctByIdmobreqrespjsonid(final Long idmobreqrespjsonid);
	Long getIdmobreqrespjsonidByMsgid(final String msgid);
	
	MobReqRespJsonId findByIdmobreqrespjsonid(long id);
	List<MobReqRespJsonId> findByMsgid(String msgId);
}
