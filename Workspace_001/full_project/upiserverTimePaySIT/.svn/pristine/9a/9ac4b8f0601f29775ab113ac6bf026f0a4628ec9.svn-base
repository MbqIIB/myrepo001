package com.npst.upiserver.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;

@Repository
public interface ReqRespAuthDetailsPayeesRepository extends JpaRepository<ReqRespAuthDetailsPayees,Long>{
	List<ReqRespAuthDetailsPayees> findByIdReqRespAuthDetails(Long idReqRespAuthDetails );
	
	ReqRespAuthDetailsPayees findByPayeeAddrAndTxnId(String payeeAddr,String txnId);

	List<ReqRespAuthDetailsPayees> findByPayeeAddrAndIdReqRespAuthDetails(String payeeVpa,Long idReqRespAuthDetails);
	
	ReqRespAuthDetailsPayees findByIdpayeescol(Long idpayeescol);

	List<ReqRespAuthDetailsPayees> findByPayeeAddrAndIdReqRespAuthDetails(String PayeeAddr,long idReqRespAuthDetails);
}
