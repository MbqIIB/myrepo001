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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.npst.upi.hor.Accountproviders;
import com.npst.upi.hor.Reqresplistaccpvd;

public class AccountProviderListDao {
	private static final Logger log = Logger.getLogger(AccountProviderListDao.class);
	private SessionFactory sessionFactory = getSessionFactory();

	public List<Accountproviders> getAccountProviderListFromDb() {
		log.trace("");
		Session session = null;
		List<Accountproviders> listAccountProvider = null;
		Reqresplistaccpvd idreqresplistaccpvd = null;
		List<Reqresplistaccpvd> reqresplistaccpvd = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Reqresplistaccpvd.class);
			cr.setMaxResults(1);
			cr.addOrder(Order.desc("resinsertdate"));
			reqresplistaccpvd = cr.list();
			idreqresplistaccpvd = reqresplistaccpvd.get(0);
			session.close();
			session = this.sessionFactory.openSession();
			Criteria crChild = session.createCriteria(Accountproviders.class);
			crChild.add(Restrictions.eq("reqresplistaccpvd", idreqresplistaccpvd));
			listAccountProvider = crChild.list();
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
		log.debug("return successfully with listAccountProvider:" + listAccountProvider);
		return new ArrayList<Accountproviders>(listAccountProvider);
	}

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
}
