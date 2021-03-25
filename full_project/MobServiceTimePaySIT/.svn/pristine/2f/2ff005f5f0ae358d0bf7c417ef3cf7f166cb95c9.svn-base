package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.npst.upi.hor.ReqrespauthdetailsPayees;

public class ReqrespauthdetailsPayeesHome {

	private static final Logger log = Logger.getLogger(ReqrespauthdetailsPayeesHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

	public List<ReqrespauthdetailsPayees> getPayee(String txnId) {
		log.trace("txnId=[" + txnId + "+");
		Session session = null;
		List<ReqrespauthdetailsPayees> list = new ArrayList<>();
		try {

			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(ReqrespauthdetailsPayees.class);
			cr.add(Restrictions.eq("txnId", txnId));
			list = cr.list();

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return list;

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
}
