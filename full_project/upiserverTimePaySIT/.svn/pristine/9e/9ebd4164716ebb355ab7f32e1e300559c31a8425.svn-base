package com.npst.upiserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npst.upiserver.entity.ListVaeEntity;
import java.lang.String;
import java.util.List;

public interface ListVaeRepo extends JpaRepository<ListVaeEntity, Long> {
	
	List<ListVaeEntity> findByAddr(String addr);

}
