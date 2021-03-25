package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.npst.upi.hor.Lov;

public class LovHome {
	private static final Logger log = Logger.getLogger(LovHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

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

	public List<Lov> selectLovByLovType(String lovType) {
		log.trace("lovType :" + lovType);
		Session session = null;
		List<Lov> results = null;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM LOV WHERE LovType = :LOVTYPE";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("LOVTYPE", lovType);
			query.addEntity(Lov.class);
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
}
