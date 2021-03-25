package com.npst.mobileservice.dao;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.MobMandateReqRespJsonIdEntity;

public class MandateMobupireqrespjsonidHome {

	private static final Logger	log				=  Logger.getLogger(MandateMobupireqrespjsonidHome.class);
	private SessionFactory		sessionFactory	= getSessionFactory();

	public MobMandateReqRespJsonIdEntity findById(long parseInt) {
		Session session = null;
		MobMandateReqRespJsonIdEntity mandateMobupireqrespjsonid = null;
		try {
			log.trace("Inside findById inside Mobupireqrespjsonid with :{}"+parseInt);
			session = this.sessionFactory.openSession();
			mandateMobupireqrespjsonid = (MobMandateReqRespJsonIdEntity) session.get(MobMandateReqRespJsonIdEntity.class, parseInt);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		log.trace("Data create mandate for mandateMobupireqrespjsonid :{}"+mandateMobupireqrespjsonid);
		log.info("Data create mandate for mandateMobupireqrespjsonid :{}"+mandateMobupireqrespjsonid);
		return mandateMobupireqrespjsonid;
	}

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void save(MobMandateReqRespJsonIdEntity mandateMobupireqrespjsonid) {
		Session session = null;
		Transaction transaction = null;
		try {
			log.info("inserting  MobMandateReqRespJsonIdEntity data :{}"+ mandateMobupireqrespjsonid);
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(mandateMobupireqrespjsonid);
			transaction.commit();
			log.info("data Successfully saved into MobMandateReqRespJsonIdEntity:{}"+mandateMobupireqrespjsonid);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
