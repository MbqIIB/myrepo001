package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.upi.hor.Feedback;
import com.npst.upi.hor.MasterConfig;
import com.npst.upi.hor.Registration;

public class FeedBackHome {
	private static final Logger log = Logger.getLogger(FeedBackHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

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

	public List<Feedback> findByRegIdAndSDate(Integer regId, Date date) {
		log.trace(regId);
		Session session = null;
		List<Feedback> results = null;
		SQLQuery query = null;
		Long count = 0L;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM feedback WHERE regId=:regId and feedback_date=:feedback_date";
			query = session.createSQLQuery(sql);
			query.addEntity(Feedback.class);
			query.setParameter("regId", regId);
			query.setParameter("feedback_date", date);
			results = query.list();
			log.info("Feedback list got : " + results);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public Registration findByRegId(Integer regId) {
		log.trace(regId);
		Session session = null;
		Registration results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regId));
			results = (Registration) cr.uniqueResult();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public RespJson saveFeedback(Feedback feedback) {
		RespJson respJson = new RespJson();
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(feedback);
			transaction.commit();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REGSUCCESS.getCode());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			session.close();
		}
		return respJson;
	}

	public List<MasterConfig> findByName(String name) {
		log.trace(name);
		Session session = null;
		List<MasterConfig> results = null;
		SQLQuery query = null;
		Long count = 0L;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM masterdetails WHERE value=:value";
			query = session.createSQLQuery(sql);
			query.addEntity(MasterConfig.class);
			query.setParameter("value", name);
			results = query.list();
			log.info("MasterConfig list got : " + results);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
}
