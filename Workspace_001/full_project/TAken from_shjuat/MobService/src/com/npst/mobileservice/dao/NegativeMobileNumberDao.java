/**
 * 
 */
package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.npst.mobileservice.service.NegativeMobileNumberService;
import com.npst.upi.hor.NegativeMobileNumber;

/**
 * @author npst
 *
 */
public class NegativeMobileNumberDao {

	private static final Logger log = Logger.getLogger(NegativeMobileNumberService.class);
	private static final SessionFactory sessionFactory = getSessionFactory();

	public NegativeMobileNumber findByMobileNumber(final String mobileNumber) {
		NegativeMobileNumber negativeMobileNumber = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			negativeMobileNumber = session.get(NegativeMobileNumber.class, mobileNumber);
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
		return negativeMobileNumber;
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

	@SuppressWarnings("unchecked")
	public List<String> findAll() {
		List<String> list = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			list = session.createCriteria(NegativeMobileNumber.class)
					.setProjection(Projections.property("mobileNumber")).list();
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
		return list;
	}

}
