package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.Mobupireqrespjson;

public class MobupireqrespjsonHome {
	private static final Logger log = Logger.getLogger(MobupireqrespjsonHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

	public Mobupireqrespjson findById(int parseInt) {
		log.trace("id" + parseInt);
		Session session = null;
		Mobupireqrespjson mobupireqrespjson = null;
		try {
			session = this.sessionFactory.openSession();
			mobupireqrespjson = session.get(Mobupireqrespjson.class, parseInt);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return mobupireqrespjson;
	}

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("HibernateListener");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void save(Mobupireqrespjson mobupireqrespjson) {
		log.info("Request for Mobupireqrespjson" + mobupireqrespjson.toString());
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(mobupireqrespjson);
			transaction.commit();
			log.info("Successfuly save request for Mobupireqrespjson");
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("Some thing went wrong while save Mobupireqrespjson request into DB {} =  {}"+e);
		} finally {
			session.close();
		}
	}

}
