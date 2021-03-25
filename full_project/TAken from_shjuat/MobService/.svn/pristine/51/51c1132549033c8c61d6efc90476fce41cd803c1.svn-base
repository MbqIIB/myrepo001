/**
 * 
 */
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

import com.npst.upi.hor.Appversion;

/**
 * @author npst
 */
public class AppVersionDao {
	private static final Logger log = Logger.getLogger(AppVersionDao.class);
	private static final SessionFactory sessionFactory = getSessionFactory();

	public List<Appversion> findByStatus() {

		Session session = null;
		List<Appversion> results = new ArrayList<Appversion>();
		try {
			session = AppVersionDao.sessionFactory.openSession();
			String sql = "select * from appversion where STATUS= 1";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Appversion.class);
			results = query.list();
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
