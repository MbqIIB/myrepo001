package com.npst.upiserver.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npst.upiserver.entity.ErrorCodeDesc;

@Repository
public interface ErrorCodeDescRepository extends JpaRepository<ErrorCodeDesc, Long>{

	List<ErrorCodeDesc> findByUpierrcode(String upierrcode);
	
	//@Query(value="select errdesc from errcodedesc where upierrcode =:errCode ")
	String findErrdescByUpierrcode(final String errCode); 
	
}
