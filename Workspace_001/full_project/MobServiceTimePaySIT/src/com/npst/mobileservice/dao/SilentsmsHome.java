package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Silentsms;

public class SilentsmsHome {
	private SessionFactory sessionFactory = getSessionFactory();
	private static final Logger log = Logger.getLogger(SilentsmsHome.class);

	private static String GETMOB = "SELECT * FROM silentsms WHERE TID=:tid AND status in (:status1, :status2) AND DEVICEID = :deviceid ORDER BY LOGDATE DESC";//AND LOGDATE > DATE_SUB(NOW(), INTERVAL :minute MINUTE)
	private static String GETDEVICEIDANDIMEI = "SELECT * FROM silentsms WHERE MOBNO = :mobileNo AND LOGDATE > DATE_SUB(NOW(), INTERVAL :minute MINUTE) ORDER BY LOGDATE DESC";
	private static String lastMobMinute = Util.getProperty("LASTMOBMINUTE");
	private static String lastMobSecond = Util.getProperty("LASTMOBSECOND");
	private static String validateOn = Util.getProperty("VALIDATEON");
	private static String UPDATE_DEVICE_ID_IMEI = "update silentsms C set C.STATUS =:STATUS where C.DEVICEID= :DEVICEID  and C.TID=:TID ";
	private static String GETMOBSECOND = "SELECT * FROM silentsms WHERE TID=:tid and status in (:status1, :status2) AND DEVICEID = :deviceid ORDER BY LOGDATE DESC";//AND LOGDATE > DATE_SUB(NOW(), INTERVAL :second SECOND)
	private static String GETTID = "SELECT * FROM silentsms WHERE TID=:tid AND status =:status AND LOGDATE > DATE_SUB(NOW(), INTERVAL :minute MINUTE) ORDER BY LOGDATE DESC";

	public List<Silentsms> getMobileNo(ReqJson reqJson) {
		log.info("Device Id for getMobile="+reqJson.getDeviceId().trim());
		log.info("Tid for getMobile="+reqJson.getTid().trim());
		Session session = null;
		List<Silentsms> results = null;
		try {
			session = this.sessionFactory.openSession();
			//SQLQuery query = session.createSQLQuery(GETMOBSECOND);
			SQLQuery query = session.createSQLQuery(validateOn!=null && validateOn.equalsIgnoreCase("SEC")?GETMOBSECOND:GETMOB);
			query.addEntity(Silentsms.class);
			/*if(validateOn!=null && validateOn.equalsIgnoreCase("SEC")) {
				query.setParameter("second", lastMobSecond);
			}
			else {
				query.setParameter("minute", lastMobMinute);
			}*/
			
			//query.setParameter("imei", reqJson.getImei());
			query.setParameter("deviceid", reqJson.getDeviceId().trim());
			query.setParameter("status1", ConstantI.SILENT_SMS_ACTIVE);
			query.setParameter("status2", ConstantI.SILENT_SMS_EXPIRED);
			query.setParameter("tid", reqJson.getTid().trim());
			results = query.list();
			log.info("results[" + JSONConvert.getJsonStr(results) + "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return results;
	}

	public List<Silentsms> getDeviceIdAndImei(String mobileNo) {
		log.trace("reqJson[" + mobileNo + "]");
		Session session = null;
		List<Silentsms> results = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(GETDEVICEIDANDIMEI);
			query.addEntity(Silentsms.class);
			query.setParameter("minute", lastMobMinute);
			query.setParameter("mobileNo", mobileNo);
			results = query.list();
			log.trace("results[" + JSONConvert.getJsonStr(results) + "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return results;
	}

	public int updateSilentData(String deviceId,String imei,String tid) {
		Session session = null;
		Transaction tx = null;
		try {
			session = this.sessionFactory.openSession();
			tx = session.beginTransaction();
			//log.info(session.createSQLQuery(GETMOBSECOND).setInteger("status", ConstantI.SILENT_SMS_INACTIVE).setString("DEVICEID", deviceId).setString("IMEI", imei).setString("TID", tid).list());
			//int updated = session.createSQLQuery(UPDATE_DEVICE_ID_IMEI).setInteger("STATUS", ConstantI.SILENT_SMS_INACTIVE).setString("DEVICEID", deviceId).setString("IMEI", imei).setString("TID", tid).executeUpdate();
			SQLQuery query = session.createSQLQuery(UPDATE_DEVICE_ID_IMEI);
			query.addEntity(Silentsms.class);
			query.setParameter("DEVICEID",deviceId);
			query.setParameter("STATUS", ConstantI.SILENT_SMS_EXPIRED);
			//query.setParameter("IMEI",imei);
			query.setParameter("TID",tid);
			int updated = query.executeUpdate();
			log.info("Updated [" + updated + "] rows in silentsms with device id and imei");
			tx.commit();
			return updated;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			tx.rollback();
			return -1;
		} finally {
			session.close();
		}
	}

	public Serializable insertLog(Silentsms silentsms) {
		log.trace("silentsms[" + silentsms.toString() + "]");
		Session session = null;
		Transaction transaction = null;
		Serializable tid = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			tid = session.save(silentsms);
			transaction.commit();
			log.trace("after save silentsms[" + silentsms.toString() + "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
         return tid;
	}
	
	public void updateLog(Silentsms silentsms) {
		log.trace("silentsms[" + silentsms.toString() + "]");
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
		
			session.update(silentsms);
			transaction.commit();
			log.trace("after save silentsms[" + silentsms.toString() + "]");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}

	}

	public Silentsms findByTidAndDeviceId(String tid, String deviceId) {
		log.info("tid for searching data mobileNoSend:{}"+tid);
		log.info("Device Id for mobileNoSend:{}"+deviceId);
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			return (Silentsms) session.createCriteria(Silentsms.class).add(Restrictions.eq("tid", tid)).add(Restrictions.eq("status", 2)).add(Restrictions.eq("deviceid", deviceId)).uniqueResult();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return null;

	}
		
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("HibernateListener");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("error in:" + s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

}
