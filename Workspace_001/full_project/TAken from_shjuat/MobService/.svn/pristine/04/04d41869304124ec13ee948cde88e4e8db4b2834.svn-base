package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.mobileservice.object.ReqJson;
import com.npst.upi.hor.Complaint;

public class ComplaintHome {

	private SessionFactory sessionFactory = getSessionFactory();
	private static final Logger log = Logger.getLogger(ComplaintHome.class);

	//Changed from criteria to sql (Himanshu Gusain)
	public boolean addComplaint(Complaint complaint) {
		log.trace("customerQuery[" + complaint + "]");
		Session session = null;
		List<Complaint> results = null;
		boolean flag = false;
		try {
			String sql = "select * from complaint where txnId=:txnId";
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Complaint.class);
			query.setParameter("txnId", complaint.getTxnId());
			results = (List<Complaint>)query.list();
			
			if (results.size() >= 1) {
				return flag;
			}
			flag = true;
			session.save(complaint);
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
		return flag;
	}

	//Changed from criteria to sql (Himanshu Gusain)
	public List<Complaint> getComplaint(ReqJson reqJson) {
		log.trace("reqJson[" + reqJson + "]");
		Session session = null;
		List<Complaint> results = null;
		try {
			String sql = "select * from complaint where mobileNo=:mobileNo ORDER BY complaintDate ";
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Complaint.class);
			query.setParameter("mobileNo", reqJson.getMobileNo());
			results = (List<Complaint>)query.list();
			if (null == results && results.size() == 0) {
				return results;
			}
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
		return results;
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
