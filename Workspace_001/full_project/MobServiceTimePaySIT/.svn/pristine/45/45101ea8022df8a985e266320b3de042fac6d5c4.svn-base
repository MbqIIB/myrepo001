/**
 * 
 */
package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.npst.upi.hor.MasterConfig;

/**
 * @author npst
 *
 */
public class MasterConfigDao {
	private static final Logger log = Logger.getLogger(RegistrationHome.class);
	private static final SessionFactory sessionFactory = getSessionFactory();

	public List<MasterConfig> findByStatus(int status) {
		Session session = null;
		List<MasterConfig> configs = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			log.info("Fetching crash analytics info from persistent store");
			configs = session.createCriteria(MasterConfig.class).add(Restrictions.eq("status", status)).list();
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
		return configs != null ? configs : Collections.emptyList();

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
