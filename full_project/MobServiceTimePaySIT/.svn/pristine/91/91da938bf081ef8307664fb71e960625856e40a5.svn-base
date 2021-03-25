package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.Mobupireqrespjsonid;

public class MobupireqrespjsonidHome {
	private static final Logger log = Logger.getLogger(MobupireqrespjsonidHome.class);
	private SessionFactory sessionFactory = getSessionFactory();
	
	private static final  String selectQ = "SELECT * FROM mobupireqrespjsonid where tpId=:tpId";

	public Mobupireqrespjsonid findById(int parseInt) {
		log.trace("id" + parseInt);
		Session session = null;
		Mobupireqrespjsonid mobupireqrespjsonid = null;
		try {
			session = this.sessionFactory.openSession();
			mobupireqrespjsonid = session.get(Mobupireqrespjsonid.class, parseInt);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return mobupireqrespjsonid;
	}

	public Mobupireqrespjsonid findByTpId(String tpId) {
		log.debug("id" + tpId);
		Session session = null;
		Mobupireqrespjsonid mobupireqrespjsonid = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(selectQ);
			query.addEntity(Mobupireqrespjsonid.class);
			query.setParameter("tpId", tpId);
			query.setMaxResults(1);
			Object ob = query.uniqueResult();
			if (ob != null) {
				mobupireqrespjsonid = (Mobupireqrespjsonid) ob;
			}
			return mobupireqrespjsonid;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return mobupireqrespjsonid;
	}
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("HibernateListener");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void save(Mobupireqrespjsonid mobupireqrespjsonid) {
		log.info("Request for Mobupireqrespjsonid" + mobupireqrespjsonid.toString());
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(mobupireqrespjsonid);
			transaction.commit();
			log.info("Successfuly save request for Mobupireqrespjsonid");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("Some thing went wrong while save Mobupireqrespjsonid request into DB {} =  {}"+e);
		} finally {
			session.close();
		}
	}
}
