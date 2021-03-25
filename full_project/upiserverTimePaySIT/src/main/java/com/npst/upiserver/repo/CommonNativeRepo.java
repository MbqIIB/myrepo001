/*package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.npst.upiserver.entity.TestEntity;

public interface CommonNativeRepo extends JpaRepository<TestEntity, Long> {
	@Query(value = "select STAN_SEQUENCE.NEXTVAL from dual", nativeQuery = true)
	Integer getStanNum();

	@Query(value = "select RRN_SEQUENCE.NEXTVAL from dual", nativeQuery = true)
	Integer getRrnNum();

	@Query(value = "select UMN_SEQ.NEXTVAL from dual", nativeQuery = true)
	Integer getUmnNum();
}
*/