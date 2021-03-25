package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.Useractivity;

public class UseractivityHome {
	private SessionFactory sessionFactory = getSessionFactory();
	private static final Logger log = Logger.getLogger(UseractivityHome.class);

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

	public void insertDetails(Useractivity userDetails) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(userDetails);
			transaction.commit();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}

	}
}
