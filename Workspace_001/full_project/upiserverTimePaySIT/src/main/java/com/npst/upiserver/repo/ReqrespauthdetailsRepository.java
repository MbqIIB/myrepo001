package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespAuthDetails;

@Repository
public interface ReqrespauthdetailsRepository extends JpaRepository<ReqRespAuthDetails, Long> {
	
	List<ReqRespAuthDetails> findByTxnTypeAndRespInsertIsNull(final String txnType); 
	
	ReqRespAuthDetails findByTxnIdAndRespInsertIsNull(final String txnId); 
	
	@Query(value="SELECT * FROM reqrespauthdetails r INNER JOIN reqrespauthdetailspayees rp ON r.idReqRespAuthDetails = rp.idReqRespAuthDetails WHERE rp.PayeeAddr = :payeeAddr AND r.TxnType = 'COLLECT' AND r.respResult IS NULL AND r.respInsert IS NULL AND r.RegId=:regId AND r.TxnConfOrgStatus IS NULL ", nativeQuery = true)
	List<ReqRespAuthDetails> getOnPayeeAddr(@Param("payeeAddr")final String payeeAddr,@Param("regId")final String regId); 
	
	List<ReqRespAuthDetails> findByTxnIdOrderByReqInsertDesc(String txnId);

	ReqRespAuthDetails findTopByTxnIdAndRespInsertIsNull(String txnId);

	@Query("SELECT r FROM ReqRespAuthDetails r INNER JOIN ReqRespAuthDetailsPayees rp ON r.idReqRespAuthDetails = rp.idReqRespAuthDetails WHERE rp.payeeAddr =?1 AND r.txnType = 'COLLECT' AND r.respResult IS NULL AND r.respInsert IS NULL AND r.regid=?2")
	List<ReqRespAuthDetails> getJointByPayeeAddrAndRegId(String payeeAddr, long regId);

}
