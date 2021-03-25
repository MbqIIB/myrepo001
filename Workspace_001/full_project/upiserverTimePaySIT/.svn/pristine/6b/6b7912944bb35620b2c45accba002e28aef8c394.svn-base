package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.ReqRespListAccPvdDao;
import com.npst.upiserver.entity.AccountProvidersEntity;
import com.npst.upiserver.entity.ReqRespListAccPvdEntity;
import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.npcischema.RespListAccPvd;
import com.npst.upiserver.npcischema.RespListAccPvd.AccPvdList.AccPvd;
import com.npst.upiserver.repo.AccountProvidersRepository;
import com.npst.upiserver.repo.ReqRespListAccPvdRepository;

@Component
public class ReqRespListAccPvdDaoImpl implements ReqRespListAccPvdDao {

	private static final Logger log = LoggerFactory.getLogger(ReqRespListAccPvdDaoImpl.class);
	@Autowired
	private ReqRespListAccPvdRepository reqRespListAccPvdRepo;
	@Autowired
	private AccountProvidersRepository accountProvidersRepo;
	private Set<String> topBankIfscPrefix = new HashSet<String>();
	private final byte topBankRankFlag = 1;
	private final ConcurrentMap<String, String> ifscAndNameCache = new ConcurrentHashMap<String, String>();
	@Override
	public void insert(final ReqListAccPvd reqListAccPvd) {
		try {
			ReqRespListAccPvdEntity reqresplistaccpvd = new ReqRespListAccPvdEntity();
			reqresplistaccpvd.setReqheadts(reqListAccPvd.getHead().getTs());
			reqresplistaccpvd.setReqheadorgid(reqListAccPvd.getHead().getOrgId());
			reqresplistaccpvd.setReqheadmsgid(reqListAccPvd.getHead().getMsgId());
			reqresplistaccpvd.setTxnid(reqListAccPvd.getTxn().getId());
			reqresplistaccpvd.setTxnts(reqListAccPvd.getTxn().getTs());
			reqresplistaccpvd.setTxntype(reqListAccPvd.getTxn().getType().value());
			reqresplistaccpvd.setReqinsertdate(new Date());
			reqRespListAccPvdRepo.save(reqresplistaccpvd);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error {}", e);
		}
	}

	@Override
	public void updateResp(final RespListAccPvd respListAccPvd) {
		try {
			log.info("Inside updateResp method ");
			ReqRespListAccPvdEntity reqresplistaccpvd = reqRespListAccPvdRepo
					.findByTxnid(respListAccPvd.getTxn().getId());
			if (null == reqresplistaccpvd) {
				log.info("Not got on TxnId");
				reqresplistaccpvd = new ReqRespListAccPvdEntity();
			}
			
			Date respDate = new Date();
			reqresplistaccpvd.setTxnid(respListAccPvd.getTxn().getId());
			reqresplistaccpvd.setTxnts(respListAccPvd.getTxn().getTs());
			reqresplistaccpvd.setTxntype(respListAccPvd.getTxn().getType().value());
			reqresplistaccpvd.setRespheadts(respListAccPvd.getHead().getTs());
			reqresplistaccpvd.setRespheadorgid(respListAccPvd.getHead().getOrgId());
			reqresplistaccpvd.setRespheadmsgid(respListAccPvd.getHead().getMsgId());
			reqresplistaccpvd.setResinsertdate(respDate);
			reqRespListAccPvdRepo.save(reqresplistaccpvd);
			log.info("reqRespListAccPvdRepo saved");
			List<RespListAccPvd.AccPvdList.AccPvd> accPvds = respListAccPvd.getAccPvdList().getAccPvd();
			Set<AccountProvidersEntity> listAccountproviders = new HashSet<>();
			AccountProvidersEntity accountproviders = null;
			for (AccPvd accPvd : accPvds) {
				accountproviders = new AccountProvidersEntity();
				accountproviders.setReqresplistaccpvd(reqresplistaccpvd);
				accountproviders.setIfsc(accPvd.getIfsc());
				accountproviders.setName(accPvd.getName());
				accountproviders.setIin(accPvd.getIin());
				accountproviders.setIsactive(accPvd.getActive().value());
				accountproviders.setLastmodifiedts(accPvd.getLastModifedTs());
				accountproviders.setProds(accPvd.getProds());
				accountproviders.setRecorddate(respDate);
				accountproviders.setSpocemail(accPvd.getSpocEmail());
				accountproviders.setSpocname(accPvd.getSpocName());
				accountproviders.setSpocphone(accPvd.getSpocPhone());
				accountproviders.setUrl(accPvd.getUrl());
				accountproviders.setMobRegFormat(accPvd.getMobRegFormat());
				if (topBankIfscPrefix.contains(accPvd.getIfsc())) {
					accountproviders.setRank(topBankRankFlag);
				}
				listAccountproviders.add(accountproviders);
				accountProvidersRepo.saveAll(listAccountproviders);
				log.info("accountProvidersRepo saved");
			}
		} catch (Exception e) {
			log.error("error {}", e);
			e.printStackTrace();

		}
	}

	@Override
	public ConcurrentMap<String, String> getIfscAndNameCache() {
		try {
			if (ifscAndNameCache.size() == 0) {
			System.out.println(topBankIfscPrefix);
			synchronized (this) {
			if (ifscAndNameCache.size() == 0) {
			updateIfscAndName(ifscAndNameCache);
			}
			}
			}
			} catch (Exception e) {
			e.printStackTrace();
			log.error("error in load getIfscAndNameCache {}", e);
			//ErrorLog.sendError(e, ReqRespListAccPvdDaoImpl.class);
			}
			return ifscAndNameCache;
			}
	private void updateIfscAndName(final ConcurrentMap<String, String> ifscAndName) {
		Set<AccountProvidersEntity> listAccountproviders = null;
		AccountProvidersEntity en = accountProvidersRepo.findFirstByOrderByIdaccountprovidersDesc();
		if (en != null) {
			listAccountproviders = en.getReqresplistaccpvd().getAccountproviderses();
			updateIfscAndName(ifscAndName, listAccountproviders);
		}

	}
	private void updateIfscAndName(final ConcurrentMap<String, String> ifscAndName,
			Set<AccountProvidersEntity> listAccountproviders) {
		for (AccountProvidersEntity accP : listAccountproviders) {
			ifscAndName.put(accP.getIfsc().toUpperCase(), accP.getName());
		}
	}
	@Override
	public void refreshIfscAndBankNameCache() {
		// TODO Auto-generated method stub
		
	}

}
