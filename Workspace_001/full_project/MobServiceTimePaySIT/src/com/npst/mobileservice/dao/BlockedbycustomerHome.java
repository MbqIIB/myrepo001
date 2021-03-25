package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.upi.hor.Blockedbycustomer;

public class BlockedbycustomerHome {
	private static final Logger log = Logger.getLogger(BlockedbycustomerHome.class);
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

	public void insert(String payeeAddr, int regId, Date blockTimeLine) {

		log.trace("String[" + payeeAddr + "]");
		Session session = null;
		Blockedbycustomer blockedbycustomer = new Blockedbycustomer();
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			blockedbycustomer.setBlockeddate(new Date());
			blockedbycustomer.setBlockedvpa(payeeAddr);
			blockedbycustomer.setExpirydate(blockTimeLine);
			blockedbycustomer.setStatus(1);
			blockedbycustomer.setRegid(regId);
			session.save(blockedbycustomer);
			transaction.commit();
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

	}

	public boolean update(Blockedbycustomer blockedbycustomer) {

		log.trace("String[" + blockedbycustomer.toString() + "]");
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(blockedbycustomer);
			transaction.commit();
			flag = true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			flag = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	public List<Blockedbycustomer> select(String blockedvpa, int regid) {
		log.trace("String[" + blockedvpa + "]");
		Session session = null;
		List<Blockedbycustomer> results = null;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM blockedbycustomer where status = 1 and regid = :regid and blockedvpa = :blockedvpa order by blockeddate desc";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Blockedbycustomer.class);
			query.setParameter("regid", regid);
			query.setParameter("blockedvpa", blockedvpa);
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

	public List<Blockedbycustomer> selectByRegId(int regid) {
		log.trace("String[" + regid + "]");
		Session session = null;
		List<Blockedbycustomer> results = null;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM blockedbycustomer where status = 1 and regid = :regid order by blockeddate desc";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Blockedbycustomer.class);
			query.setParameter("regid", regid);
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

}
