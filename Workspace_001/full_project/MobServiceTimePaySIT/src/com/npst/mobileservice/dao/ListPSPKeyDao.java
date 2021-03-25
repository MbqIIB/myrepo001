package com.npst.mobileservice.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;

import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.ListPspKeysEntity;

public class ListPSPKeyDao {
	private static final Logger log = Logger.getLogger(ListPSPKeyDao.class);
	private SessionFactory				sessionFactory					= getSessionFactory();
	
	String sql = "select * from LIST_PSP_KEYS_TAB where PSP_ORG_ID=:OrgId ORDER BY PSPKEYS_ID DESC";
	
	public ListPspKeysEntity getDetail(String orgid) {
		Session session = null;
		ListPspKeysEntity listPsp = null;
		try {
			log.trace("finding key on orgid :{}"+orgid);
			session = this.sessionFactory.openSession();
			SQLQuery qry = session.createSQLQuery(sql);
			qry.addEntity(ListPspKeysEntity.class);
			qry.setParameter(ConstantI.ORGID, orgid);
			listPsp = (ListPspKeysEntity)qry.uniqueResult();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listPsp;
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
