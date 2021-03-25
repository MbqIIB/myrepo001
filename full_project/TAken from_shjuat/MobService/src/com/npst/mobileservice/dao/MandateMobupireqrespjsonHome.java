package com.npst.mobileservice.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.MobMandateReqRespJsonEntity;

public class MandateMobupireqrespjsonHome {

	private static final Logger log = Logger.getLogger(MandateMobupireqrespjsonHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

	public MobMandateReqRespJsonEntity findById(long parseInt) {
		Session session = null;
		MobMandateReqRespJsonEntity mandateMobupireqrespjson = null;
		try {
			log.info("fetching mobupireqrespjson for id : {}"+parseInt);
			session = this.sessionFactory.openSession();
			mandateMobupireqrespjson = (MobMandateReqRespJsonEntity) session.get(MobMandateReqRespJsonEntity.class, parseInt);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return mandateMobupireqrespjson;
	}

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateListener.getSessionFactory();
		} catch (Exception ex) {
			log.info(ex.getMessage(),ex);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void save(MobMandateReqRespJsonEntity mandateMobupireqrespjson) {
		log.info("Execution start of Save mobupireqrespjson:{}"+mandateMobupireqrespjson);
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(mandateMobupireqrespjson);
			transaction.commit();
			log.info("data Successfully saved into DB:{}"+mandateMobupireqrespjson);
		} catch (Exception ex) {
			log.info("Some error occured while Save mobupireqrespjson:{}"+ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
