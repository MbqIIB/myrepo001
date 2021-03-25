package com.npst.upiserver.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespListAccount;

@Repository
public interface ReqRespListAccountRepository extends JpaRepository<ReqRespListAccount, Long> {
	//ReqRespListAccount findByTxnId(String txnId);

	Long countByRespErrCodeAndLinkAndReqInsertDate(String resperrcode, String link, Date lastlogindt);
	
	List<ReqRespListAccount> findByTxnId(String txnId);
}
