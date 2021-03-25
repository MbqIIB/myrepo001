package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.entity.Registration;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
	
	Long countByRegidAndStatus(Long regid,Integer status);
	Registration findByRegid(Long regid);
	Registration findByMobnoAndStatus(String mobno ,Integer status);
	
	@Query("select new com.npst.upiserver.dto.RegDto(reg.regid,reg.defvpa,reg.status,reg.mobno,reg.appos,reg.gcmtoken) from Registration reg where reg.regid=?1")
	RegDto findDtoByRegId(Long regId);
	
	@Query(nativeQuery=true ,value="select distinct regid from registration where defvpa =?1 and status=?2")
	List<Object> getRegIdByDefVpaAndStatus(String vpa,int status);
	
	@Query("select new com.npst.upiserver.dto.RegDto(reg.regid,reg.defvpa,reg.status,reg.mobno,reg.appos,reg.gcmtoken) from Registration reg where reg.regid=?1")
	RegDto findDtoByRegId(long regId);
	
	long countByRegidAndStatus(long regId,int status);
	
	long countByRegidAndStatusIn(long regId,List<Integer> status);
	
	@Query(nativeQuery=true ,value="SELECT DISTINCT REGID FROM REGISTRATION where DEFVPA =?1")
	List<Object> getRegIdByDefVpa(String vpa);
}
