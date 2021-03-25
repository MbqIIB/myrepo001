package com.npst.mobileservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.npst.mobileservice.util.HibernateListener;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.DeviceRegLimitEntity;

public class DeviceRegLimitService {
	private static final Logger log = Logger.getLogger(DeviceRegLimitService.class);
	private static SessionFactory sessionFactory = HibernateListener.getSessionFactory();
	private static final String updateDateStrAndCountAnMBByDevId = "update DEVICE_REGISTER_LIMIT set DATE_STR=:dateStr,MOB_NUMBERS=:mbNumbers,COUNT=0 where DEVICE_ID=:deviceId";
	private static final String updateDateStrAndCountByDevId = "update DEVICE_REGISTER_LIMIT set DATE_STR=:dateStr ,COUNT=0 where DEVICE_ID=:deviceId";
	private static final String updateMobByDevId = "update DEVICE_REGISTER_LIMIT set MOB_NUMBERS=:mbNumbers where DEVICE_ID=:deviceId";

	private static final String updateCountByDevId = "update DEVICE_REGISTER_LIMIT set COUNT=COUNT+1 where DEVICE_ID=:deviceId";

	private static int MAX_REGLIMIT_ADAY = StringUtils.isBlank(Util.getProperty("MAX_REGLIMIT_ADAY")) ? 3
			: Integer.parseInt(Util.getProperty("MAX_REGLIMIT_ADAY"));

	private static String MAX_REGLIMIT_ADAY_ERROR = StringUtils.isBlank(Util.getProperty("MAX_REGLIMIT_ADAY_ERROR"))
			? "XB"
			: Util.getProperty("MAX_REGLIMIT_ADAY_ERROR");

	private static DeviceRegLimitService deviceRegLimitService = new DeviceRegLimitService();

	private DeviceRegLimitService() {

	}

	public static DeviceRegLimitService getInstance() {
		return deviceRegLimitService;
	}

	public String getLimitErrorMsg() {
		return MAX_REGLIMIT_ADAY_ERROR;
	}

	public boolean isDeviceRegLmtAllowed(String deviceId, String mbNumber) throws Exception {
		log.info("start isDeviceRegLmtAllowed deviceId={} ,mbNumber={}"+deviceId + "is"+mbNumber);
		boolean f = false;
		mbNumber = mbNumber.replaceFirst("\\+", "");
		DeviceRegLimitEntity en = getRegLimitByDeviceId(deviceId);
		if (en == null) {
			f = saveNew(deviceId, mbNumber);
		} else {
			if (!en.isActive()) {
				return false;
			}
			String d = LocalDate.now().toString();
			boolean mobupt = !(mbNumber.matches(en.getMobNumbers()));
			if (d.equals(en.getDateStr())) {
				if (en.getCount() < MAX_REGLIMIT_ADAY) {
					f = true;
				} else {
					log.info("MAX_DEVICE_REG_LIMIT_DAY exceeded for deviceId={} , mbNumber={}"+deviceId + "is"+mbNumber);
				}
				if (mobupt) {
					updateMbnumbers(deviceId, en.getMobNumbers() + "|" + mbNumber);
				}
			} else {
				f = true;
				if (mobupt) {
					updateDateStrMobCount(deviceId, d, en.getMobNumbers() + "|" + mbNumber);
				} else {
					updateDateStrCount(deviceId, d);
				}
			}
		}
		log.info("end isDeviceRegLmtAllowed ={} ,deviceId={}"+ f+""+ deviceId);
		return f;
	}

	private DeviceRegLimitEntity getRegLimitByDeviceId(String deviceId) throws Exception {
		Session session = null;
		DeviceRegLimitEntity ob = null;
		try {
			session = sessionFactory.openSession();
			ob = session.get(DeviceRegLimitEntity.class, deviceId);
		} catch (Exception e) {
			log.info("error in RegLimitByDeviceId msg={} ,error={}"+e.getMessage()+""+ e);
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return ob;
	}

	private boolean saveNew(String deviceId, String mbNumber) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean f = false;
		try {
			DeviceRegLimitEntity en = new DeviceRegLimitEntity();
			en.setDeviceId(deviceId.trim());
			en.setActive(true);
			en.setCount(0);
			en.setCreatedDate(new Date());
			en.setMobNumbers(mbNumber);
			en.setDateStr(LocalDate.now().toString());
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.save(en);
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.info("causeMsg={} ,error={}"+ e.getMessage()+""+e);
			if (txn != null ) {
				txn.rollback();
			}
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	private boolean updateDateStrCount(String deviceId, String dateStr) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updateDateStrAndCountByDevId).setParameter("deviceId", deviceId).setParameter("dateStr",
					dateStr);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.info("error in update current dateStr limit {}"+ e);
			if (txn != null ) {
				txn.rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	private boolean updateDateStrMobCount(String deviceId, String dateStr, String mbNumbers) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updateDateStrAndCountAnMBByDevId).setParameter("deviceId", deviceId)
					.setParameter("dateStr", dateStr).setParameter("mbNumbers", mbNumbers);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.info("error in update current dateStr limit {}"+ e);
			if (txn != null) {
				txn.rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	private boolean updateMbnumbers(String deviceId, String mbNumbers) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery)  session.createSQLQuery(updateMobByDevId).setParameter("deviceId", deviceId).setParameter("mbNumbers", mbNumbers);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.info("error in update mbNumbers limit {}"+ e);
			if (txn != null) {
				txn.rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}

	public boolean updatePlusCount(String deviceId) {
		boolean f = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			SQLQuery q =  (SQLQuery) session.createSQLQuery(updateCountByDevId).setParameter("deviceId", deviceId);
			q.executeUpdate();
			txn.commit();
			f = true;
		} catch (Exception e) {
			log.info("error in update count limit {}"+ e);
			if (txn != null) {
				txn.rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return f;
	}
}
