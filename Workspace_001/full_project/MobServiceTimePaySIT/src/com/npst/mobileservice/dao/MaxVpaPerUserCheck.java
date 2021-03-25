package com.npst.mobileservice.dao;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import com.npst.mobileservice.util.HibernateListener;
import com.npst.mobileservice.util.Util;

public class MaxVpaPerUserCheck {
	private static final Logger log =Logger.getLogger(MaxVpaPerUserCheck.class);
	private static SessionFactory sessionFactory = HibernateListener.getSessionFactory();
	private static String queryStr = Util.getProperty("MAX_VPA_PER_USER_QUERY");
	public static String ERROR_CODE = Util.getProperty("MAX_VPA_PER_USER_ERRORCODE");
	private static int MAX_VPA_COUNT = Integer.parseInt(Util.getProperty("MAX_VPA_COUNT_PER_USER"));
	private static String REGID = "REGID";
	private static MaxVpaPerUserCheck maxVpaPerUserCheck = new MaxVpaPerUserCheck();

	private MaxVpaPerUserCheck() {
	}

	public static MaxVpaPerUserCheck getInstance() {
		return maxVpaPerUserCheck;
	}

	public boolean isAllow(long regId, String payerAddr) {
		Session session = null;
		boolean f = false;
		try {
			session = sessionFactory.openSession();
			log.info("checking whether MaxVpaPerUserCheck allowed for REGID "+regId);
			session = sessionFactory.openSession();
			List<String> list = (List) (session.createSQLQuery(queryStr).setParameter(REGID, regId)).list();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (payerAddr.equalsIgnoreCase(list.get(i))) {
						f = true;
						break;
					}
				}
				if (!f) {
					if (list.size() < MAX_VPA_COUNT) {
						f = true;
					}
				}
			} else {
				f = true;
			}
		} catch (Exception e) {
			log.error("error ="+ e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}
}
