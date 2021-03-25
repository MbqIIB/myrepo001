package com.npst.upiserver.dao.impl;

import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.npst.upiserver.dao.TransNoDao;
import com.npst.upiserver.util.Util;

@Component
public class TransNoDaoImpl implements TransNoDao {
	
	private static final Logger log = LoggerFactory.getLogger(TransNoDaoImpl.class);
	/*private static final int max=Integer.parseInt(Util.getProperty("ATOMIC_MAX_F11"));
	private static final int min=Integer.parseInt(Util.getProperty("ATOMIC_MIN_F11"));
	private static final AtomicInteger atom=new AtomicInteger(Integer.parseInt(Util.getProperty("ATOMIC_START_F11")));	*/
		@Autowired
	private EntityManagerFactory emf;

	@Override
	public synchronized Integer getTransNo(Integer orgId) {
		EntityManager em = null;
		try {
			if (orgId == 0) {
				return null;
			} else {
				em = emf.createEntityManager();
				em.getTransaction().begin();
				StoredProcedureQuery query = em.createStoredProcedureQuery("getTrans")
						.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.OUT)
						.registerStoredProcedureParameter("p_OrgId", Integer.class, ParameterMode.IN)
						.setParameter("p_OrgId", orgId);
				query.execute();
				Integer p_id = (Integer) query.getOutputParameterValue("p_id");
				em.getTransaction().commit();
				return p_id;
			}
		} catch (Exception e) {
			log.error("error in getting transNo e={}", e);
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return null;
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
	
	
	//by ashish
	
/*
	@Override
	public Integer getTransNo(Integer orgId) {
		int i = atom.getAndIncrement();
		if (i < max) {
			return i;
		} else if (i == max) {
			atom.set(min + 1);
			i = min;
		} else {
// (i>max)
			try {
				Thread.sleep(600);
				i = atom.getAndIncrement();
				log.warn("getting max > ie=" + i);
			} catch (Exception e) {
				log.error("error {}");
			}
		}
		return i;
	} */
	
}
