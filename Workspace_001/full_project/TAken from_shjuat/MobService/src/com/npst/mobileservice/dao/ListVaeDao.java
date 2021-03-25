package com.npst.mobileservice.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.ListVaeEntity;

public class ListVaeDao {
	private static final Logger log = Logger.getLogger(ListVaeDao.class);
	private SessionFactory				sessionFactory					= getSessionFactory();
	
	String sql = "select * from LIST_VAE_TAB where VPA=:vpa";
	
	@SuppressWarnings("deprecation")
	public ListVaeEntity getDetail(String vpa) {
		Session session = null;
		ListVaeEntity listvae = null;
		try {
			log.trace("finding registration record for mobileNo :{}"+ vpa);
			session = this.sessionFactory.openSession();
			SQLQuery qry = session.createSQLQuery(sql);
			qry.addEntity(ListVaeEntity.class);
			qry.setParameter(ConstantI.MERVPA, vpa);
			listvae = (ListVaeEntity)qry.list().get(0);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listvae;
	}
	
	protected SessionFactory getSessionFactory() {
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
}
