package com.npst.mobileservice.dao;// default package

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.npst.upi.hor.Reqrespauthdetails;

public class ReqrespauthdetailsHome {

	private static final Logger log = Logger.getLogger(ReqrespauthdetailsHome.class);
	private SessionFactory sessionFactory = getSessionFactory();
	private static final String PENDINGCOLLECTSQL = "select * from reqrespauthdetails where regid=:regid and txnType= 'COLLECT' and ((RespResult='null') or (RespResult is null)) and ((RespInsert='null') or (RespInsert is null)) and ((TxnConfOrgStatus='null') or (TxnConfOrgStatus is null))";
	private static final String PENDINGCOLLECTSQL_COUNT = "select count(*) from reqrespauthdetails where regid=:regid and txnType= 'COLLECT' and ((RespResult='null') or (RespResult is null)) and ((RespInsert='null') or (RespInsert is null)) and ((TxnConfOrgStatus='null') or (TxnConfOrgStatus is null))";
	
	public List<Reqrespauthdetails> getPendingCollect(long regId) {
		log.trace("regid=[" + regId + "+");
		Session session = null;
		List<Reqrespauthdetails> list = new ArrayList<>();
		try {

			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(PENDINGCOLLECTSQL);
			query.addEntity(Reqrespauthdetails.class);
			query.setParameter("regid", regId);
			list = query.list();
			log.trace("list=[" + list + "+");
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
	
	public int getPendingCollectCount(int regId) {
		log.trace("regid=[" + regId + "+");
		Session session = null;
		BigInteger count=new BigInteger("0");
		try {

			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(PENDINGCOLLECTSQL_COUNT);
			query.setParameter("regid", regId);
			List list =query.list();
			if(list!=null && list.size()==1)
			{
				count= (BigInteger)list.get(0);
			}
			log.trace("count=[" + count + "+");
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
		return count.intValue();

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
