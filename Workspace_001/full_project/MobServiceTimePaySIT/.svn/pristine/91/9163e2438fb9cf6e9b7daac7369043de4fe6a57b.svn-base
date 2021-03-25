package com.npst.mobileservice.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.npst.mobileservice.cache.Cache;
import com.npst.mobileservice.dao.AccountProviderListDao;
import com.npst.mobileservice.object.AccountProviderListResp;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Accountproviders;

public class AccountListProviderCache implements Cache<Object, Object> {
	private static final Logger log = Logger.getLogger(AccountListProviderCache.class);
	private static AccountListProviderCache accountListProviderCache = null;

	public static AccountListProviderCache getInstance() {
		log.debug("Starting from getInstance: AccountListProviderCache");
		if (accountListProviderCache == null) {
			synchronized (AccountListProviderCache.class) {
				if (accountListProviderCache == null) {
					accountListProviderCache = new AccountListProviderCache();
				}
			}
		}
		log.debug("Return success from getInstance: AccountListProviderCache");
		return accountListProviderCache;
	}

	private Map<Object, Object> cacheMap;

	private AccountListProviderCache() {
		cacheMap = new ConcurrentHashMap<Object, Object>();
		cacheMap.put("accountProviders", getProviderList());
	}

	@Override
	public Object get(Object key) {
		return cacheMap.get(key);
	}

	private List<AccountProviderListResp> getProviderList() {
		log.trace("");
		AccountProviderListResp accountProviderListResp = null;
		AccountProviderListDao accountProviderListDao = new AccountProviderListDao();
		List<AccountProviderListResp> accountProviderList = new ArrayList<AccountProviderListResp>();
		List<Accountproviders> listAccountProvider = accountProviderListDao.getAccountProviderListFromDb();
		if (listAccountProvider != null && !listAccountProvider.isEmpty()) {
			for (Accountproviders accountProvider : listAccountProvider) {
				accountProviderListResp = new AccountProviderListResp();
				accountProviderListResp.setName(Util.checkNull(accountProvider.getName()));
				accountProviderListResp.setIin(Util.checkNull(accountProvider.getIin()));
				accountProviderListResp.setIfsc(Util.checkNull(accountProvider.getIfsc()));
				accountProviderListResp.setActive(Util.checkNull(accountProvider.getIsactive()));
				accountProviderListResp.setRank(String.valueOf(accountProvider.getRank()));
				accountProviderListResp.setMobRegFormat(Util.checkNull(accountProvider.getMobRegFormat()));
				accountProviderList.add(accountProviderListResp);
			}
		}
		return accountProviderList;
	}

	public static AccountListProviderCache refresh() {
		log.debug("Starting from refresh: AccountListProviderCache");
		// if (accountListProviderCache == null) {
		synchronized (AccountListProviderCache.class) {
			if (accountListProviderCache == null) {
				accountListProviderCache = new AccountListProviderCache();
			}
			// }
		}
		log.debug("Return success from refresh: AccountListProviderCache");
		return accountListProviderCache;
	}
}
