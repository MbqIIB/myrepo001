package com.npst.upiserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dto.ListVaeDto;
import com.npst.upiserver.entity.ListVaeEntity;
import com.npst.upiserver.repo.ListVaeRepo;
import com.npst.upiserver.service.QrIntantService;
@Service
public class QrIntantServiceImpl implements QrIntantService{
	
	@Autowired
	private ListVaeRepo listVaeRepo;

	@Override
	public List<ListVaeDto> findByAddr(String vpa) {
		List<ListVaeEntity> isMerchant	= listVaeRepo.findByAddr(vpa);
		ListVaeDto dto = null;
		for (ListVaeEntity listVaeEntity : isMerchant) {
			dto.setKeyCode(listVaeEntity.getKeyCode());
			dto.setKeyKi(listVaeEntity.getKeyKi());
			dto.setKeyType(listVaeEntity.getKeyValue());
			dto.setKeyType(listVaeEntity.getKeyType());
		}
		return (List<ListVaeDto>) dto;
	}

	
	public boolean isNotMerchantISVarifyed(String payeeVpa) {
		List<ListVaeEntity> isMerchant	= listVaeRepo.findByAddr(payeeVpa);
		if(!isMerchant.isEmpty()) {
		return false;
	}
		return true;
}
}
