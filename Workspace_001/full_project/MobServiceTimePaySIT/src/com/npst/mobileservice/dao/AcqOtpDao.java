package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.npst.upi.hor.AcqOtpRecord;

public class AcqOtpDao {
	private SessionFactory sessionFactory = getSessionFactory();
	String sql = "select * from acqotprecord where mobileNo=:mobileNo and isUsed=:isUsed";
	String updateSql = "update acqotprecord set isUsed=1, reason='Not Used' where mobileNo=:mobileNo and isUsed=:isUsed";
	private static final Logger log = Logger.getLogger(AcqOtpDao.class);

	public List<AcqOtpRecord> getOnMobileNoAndIsUsed(String mobileNo, int i) {
		Session session = null;
		List<AcqOtpRecord> results = new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(AcqOtpRecord.class);
			query.setParameter("mobileNo", mobileNo);
			query.setParameter("isUsed", i);
			results = query.list();
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

	public List<AcqOtpRecord> getOnMobileNoAndIsUsedAndUpdate(String mobileNo, int i) {
		Session session = null;
		List<AcqOtpRecord> results = new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(updateSql);
			query.addEntity(AcqOtpRecord.class);
			query.setParameter("mobileNo", mobileNo);
			query.setParameter("isUsed", i);
			query.executeUpdate();
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

	public AcqOtpRecord save(AcqOtpRecord entity) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.save(entity);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return entity;
	}

	public AcqOtpRecord update(AcqOtpRecord entity) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.update(entity);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return entity;
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
