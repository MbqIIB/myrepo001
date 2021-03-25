/**
 * 
 */
package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.CrashAnalyticsInfo;

/**
 * @author npst
 *
 */
public class CrashAnalyticsInfoDao {
	private static final Logger log = Logger.getLogger(CrashAnalyticsInfoDao.class);
	private static final SessionFactory sessionFactory = getSessionFactory();

	public Long addExceptionLog(final CrashAnalyticsInfo exceptionLog) {
		log.trace("");
		Session session = null;
		Transaction transaction = null;
		Long serializable = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			serializable = (Long) session.save(exceptionLog);
			transaction.commit();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null)
				session.close();
		}
		return serializable;

	}

	private static SessionFactory getSessionFactory() {
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
