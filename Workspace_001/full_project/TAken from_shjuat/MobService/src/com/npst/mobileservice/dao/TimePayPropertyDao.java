/**
 * 
 */
package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.npst.upi.hor.TimePayProperty;

/**
 * @author npst
 *
 */
public class TimePayPropertyDao {

	private static final Logger log = Logger.getLogger(TimePayPropertyDao.class);
	private static SessionFactory sessionFactory = getSessionFactory();

	protected static SessionFactory getSessionFactory() {
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
	public List<TimePayProperty> timePayProperties() {

		Session session = null;
		List<TimePayProperty> properties = null;

		try {
			session = sessionFactory.openSession();
			final Criteria propertiesCr = session.createCriteria(TimePayProperty.class);
			properties = propertiesCr.list();

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
		log.debug("return successfully with properties:" + properties);
		return properties;
	}

}
