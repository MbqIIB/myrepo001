package com.npst.upiserver.dao.impl;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dao.AcqTxnLimitDao;

@Component
public class AcqTxnLimitDaoImpl implements AcqTxnLimitDao {
	private static final Logger log = LoggerFactory.getLogger(AcqTxnLimitDaoImpl.class);
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Value("${PAY_ACQTXNLIMIT_UPDATE_COUNTQUERY}")
	private String payAcqTxnLimitUpdateCountQ;

	@Value("#{'${PAY_ACQTXNLIMIT_UPDATE_COUNTQUERY}'.split(',')}")
	private Set<String> errorList;

	@Value("${IS_ERROR_LIST_ALLOWED}")
	boolean isErrorListAllowed;

	@Value("${IS_UPDATE_PRE_APP_SUC_ACQTXNLIMIT}")
	boolean isUpdatePreApAcqTxnLimit;

	public boolean isUpdatePreApAcqTxnLimit() {
		return isUpdatePreApAcqTxnLimit;
	}

	@Override
	public void updateFailure(String txnId, String errorCode, String result) {
		EntityManager entityManager = null;
		int i = 0;
		try {
			if ("FAILURE".equalsIgnoreCase(result)) {
				if (isErrorListAllowed) {
					if (!errorList.contains(errorCode)) {
						return;
					}
				}
				entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();
				i = entityManager.createNativeQuery(payAcqTxnLimitUpdateCountQ).setParameter(1, txnId).executeUpdate();
				entityManager.getTransaction().commit();
			}
		} catch (Exception e) {
			log.error("error_1 {}", e);
			try {
				if (entityManager != null && entityManager.getTransaction() != null
						&& entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
			} catch (Exception ex) {
				log.error("error_2 {}", ex);
			}

		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
			log.info("AcqTxnLimit executeUpdate={} ,txnId={} ,errorCode={} ,result={}", i, txnId, errorCode, result);
		}

	}

}
