package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ListAccountDao;
import com.npst.upiserver.entity.ListAccount;
import com.npst.upiserver.repo.ListAccountRepository;

@Component
public class ListAccountDaoImpl implements ListAccountDao {
	private static final Logger log = LoggerFactory.getLogger(ListAccountDaoImpl.class);
	
	@Autowired
	ListAccountRepository listAccRepo;
	
	@Override
	public List<ListAccount> findName(String virtualId) {
		return listAccRepo.findByVpa(virtualId);
	}

	@Override
	public List<ListAccount> findName(String virtualId, String flag) {
		List<ListAccount> listAccount=new ArrayList<ListAccount>();
		try {
			if (ConstantI.CONST_R.equalsIgnoreCase(flag)) {
				listAccount=listAccRepo.findByVpaAndPstatusAndDefaultAcc(virtualId, ConstantI.CONST_ACTIVE,ConstantI.Y);
			}
			else {
				listAccount=listAccRepo.findByVpaAndPstatus(virtualId, ConstantI.CONST_ACTIVE);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return listAccount;
	}

	@Override
	public String getMobileNo(String virtualId) {
		return null;
	}

}
