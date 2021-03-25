package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.Mobupireqrespjsonid;


@Repository
public interface MobUpiReqRespJsonIdRepository extends JpaRepository<Mobupireqrespjsonid, Long>{
	Mobupireqrespjsonid findByIdmobreqrespjsonid(Long idmobreqrespjsonid);
	List<Mobupireqrespjsonid> findByMsgid(String msgId);
	Mobupireqrespjsonid findByIdmobreqrespjsonid(long id);

}
