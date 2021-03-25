package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.npst.mobileservice.util.ConstantI;
import com.npst.upi.hor.Securityencryption;

public class SecurityEncryptionDao {
	private SessionFactory sessionFactory = getSessionFactory();
	private static final Logger log = Logger.getLogger(SecurityEncryptionDao.class);

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

	public boolean insert(Securityencryption securityencryption) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(securityencryption);
			transaction.commit();
			flag = true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			flag = false;
		} finally {
			session.close();
		}
		return flag;

	}

	public Securityencryption getUserByDeviceId(String deviceId) {
		log.info(deviceId.trim());
		Session session = null;
		Securityencryption results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Securityencryption.class);
			cr.add(Restrictions.eq("deviceid", deviceId.trim()));
			cr.add(Restrictions.eq("status", ConstantI.ACTIVE_REGVPA));
			cr.addOrder(Order.desc("createddate"));
			cr.setMaxResults(1);
			results = (Securityencryption) cr.uniqueResult();
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

	public boolean update(Securityencryption securityencryption) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(securityencryption);
			transaction.commit();
			flag = true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		return flag;

	}
}
