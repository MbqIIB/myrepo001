package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.npst.mobileservice.util.ConstantI;
import com.npst.upi.hor.CustomerVPA;
import com.npst.upi.hor.Customeraccount;
import com.npst.upi.hor.ReservedVPA;

public class CustomeraccountHome {
	private static final Logger		log								= Logger.getLogger(CustomeraccountHome.class);
	private SessionFactory			sessionFactory					= getSessionFactory();
	private static StringBuilder	SEL_ACTIVE_INACTIVE_VPA			= new StringBuilder();
	private static StringBuilder	UPDATE_ACCOUNT_STATUS			= new StringBuilder();
	private static final String SEL_LIST_ACCOUNT_COUNT_BY_REGID = " select count(*) from customeraccount where regid=:regid ";
	static {
		SEL_ACTIVE_INACTIVE_VPA.append("SELECT distinct accvirtualid FROM customeraccount ");
		SEL_ACTIVE_INACTIVE_VPA.append("where regid=:regid and accrefnumber =:accrefnumber");
		SEL_ACTIVE_INACTIVE_VPA.append(
				" and (status = " + ConstantI.NORMAL_ACCOUNT + " or status= " + ConstantI.INACTIVE_ACCOUNT + " )");
		
		UPDATE_ACCOUNT_STATUS.append("update customeraccount set status = :status  ");
		UPDATE_ACCOUNT_STATUS.append(
				"where accrefnumber=:accrefnumber and accvirtualid=:accvirtualid and regid=:regid and defaccount!=:defaccount");
		
	}
	
	public Boolean checkReservedVPA(String virtualId) {
		log.debug("checking virtualId : " + virtualId + " in ReservedVPA list");
		Session session = null;
		Boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			final Criteria cri = session.createCriteria(ReservedVPA.class);
			cri.add(Restrictions.eq("virtualId", virtualId));
			cri.setProjection(Projections.rowCount());
			Object count = cri.uniqueResult();
			if (count != null && (long) count != 0) {
				flag = true;
			}
		} catch (Exception e) {
			
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return flag;
	}
	
	public boolean checkVPA(String vpa) {
		Session session = null;
		List<Customeraccount> listAccount = null;
		boolean isVpaExits = true;
		try {
			session = this.sessionFactory.openSession();
			Criteria crAccDet = session.createCriteria(Customeraccount.class);
			crAccDet.add(Restrictions.eq("accvirtualid", vpa));
			listAccount = crAccDet.list();
			if (listAccount != null && listAccount.size() > 0) {
				isVpaExits = true;
			} else {
				isVpaExits = false;
			}
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
		return isVpaExits;
	}
	
	public boolean deleteVpa(final String payeeaddr, final long regId) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			String sqlUpdate = "update customeraccount c set c.STATUS = :status, c.DELETEDDATE= :deleteddate where c.ACCVIRTUALID = :accvirtualid and c.REGID= :regid";
			int updatedEntities = session.createSQLQuery(sqlUpdate)
					.setInteger(ConstantI.STATUS, ConstantI.DELETE_ACCOUNT).setString(ConstantI.ACCVIRTUALID, payeeaddr)
					.setLong(ConstantI.REGID, regId).setDate("deleteddate", new Date()).executeUpdate();
			tx.commit();
			session.close();
			return updatedEntities >= 1;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return false;
		
	}
	
	public Boolean deregisterDefaultAcc(String virtualId, Customeraccount custAccount) {
		Session session = null;
		Transaction transaction = null;
		Boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query dergisterDefaultAccQuery = session.createSQLQuery(
					"update customeraccount set defaccount = 2 where status = :status and accvirtualid = :accvirtualid and defaccount = 1");
			dergisterDefaultAccQuery.setParameter(ConstantI.ACCVIRTUALID, virtualId);
			dergisterDefaultAccQuery.setParameter(ConstantI.STATUS, "1");
			int count = dergisterDefaultAccQuery.executeUpdate();
			Criteria cr = session.createCriteria(Customeraccount.class);
			session.saveOrUpdate(custAccount);
			flag = true;
			
			transaction.commit();
		} catch (Exception ex) {
			StringWriter s;
			ex.printStackTrace(new PrintWriter(s = new StringWriter()));
			ex.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	public Customeraccount getActiveAccountByAccountAndVpaAndRegId(final String accrefnumber, final long regId,
			final String accvirtualid) {
		log.trace("accrefnumber:" + accrefnumber + "regId:" + regId);
		System.out.println("accrefnumber:" + accrefnumber + " regId:" + regId + " accvirtualid" + accvirtualid);
		Session session = null;
		List listAccount = null;
		
		try {
			session = this.sessionFactory.openSession();
			final Criteria crAccDet = session.createCriteria(Customeraccount.class);
			crAccDet.add(Restrictions.eq("accrefnumber", accrefnumber));
			crAccDet.add(Restrictions.eq("accvirtualid", accvirtualid));
			crAccDet.add(Restrictions.eq(ConstantI.REGID,regId));
			crAccDet.add(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			listAccount = crAccDet.list();
			
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
		log.debug("return successfully with listAccount:" + listAccount);
		return listAccount != null && listAccount.size() > 0 ? (Customeraccount) listAccount.get(0) : null;
	}
	
	public List<Customeraccount> getActiveListAccountByVPAAndRegVPAId(String vPA, Long regVPAId) {
		log.trace("vPA:" + vPA + "regVPAId:" + regVPAId);
		Session session = null;
		List<Customeraccount> listAccount = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria crAccDet = session.createCriteria(Customeraccount.class);
			crAccDet.add(Restrictions.eq("accvirtualid", vPA));
			crAccDet.add(Restrictions.eq(ConstantI.REGID, regVPAId));
			crAccDet.add(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			listAccount = crAccDet.list();
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
		log.debug("return successfully with listAccount:" + listAccount);
		return listAccount;
	}
	
	public List<String> getAllVPAForActiveAccount(String accRefNumber, long regId) {
		Session session = null;
		List<String> listAccount = null;
		
		try {
			session = this.sessionFactory.openSession();
			final Criteria crAccDet = session.createCriteria(Customeraccount.class);
			crAccDet.setProjection(Projections.property(ConstantI.ACCVIRTUALID));
			crAccDet.add(Restrictions.eq("accrefnumber", accRefNumber));
			crAccDet.add(Restrictions.eq(ConstantI.REGID, regId));
			crAccDet.add(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			
			listAccount = crAccDet.list();
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
		return listAccount;
	}
	
	public List<String> getActiveInactiveVPAForAccount(String accRefNumber, long regId) {
		Session session = null;
		List<String> vpaList = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery qry = session.createSQLQuery(SEL_ACTIVE_INACTIVE_VPA.toString());
			qry.setParameter("regid", regId);
			qry.setParameter("accrefnumber", accRefNumber);
			vpaList = qry.list();
			log.trace("After Result VPA List :" + vpaList.toString());
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
		return vpaList;
	}
	
	public List<Customeraccount> getAllVpas(long regid) {
		Session session = null;
		List<Customeraccount> results = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery qry = session.createSQLQuery(
					"SELECT * FROM customeraccount where regid=:regid and DEFACCOUNT=1 and (status=1 or status=2)");
			qry.addEntity(Customeraccount.class);
			qry.setParameter("regid", regid);
			results = qry.list();
			log.trace("After Commit" + results.toString());
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
	
	public Customeraccount getDefAcc(Long regid, String accvirtualid) {
		Session session = null;
		Customeraccount results = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery qry = session.createSQLQuery(
					"SELECT * FROM customeraccount where REGID=:regid and  ACCVIRTUALID=:accvirtualid and DEFACCOUNT=1");
			qry.addEntity(Customeraccount.class);
			qry.setParameter("regid", regid);
			qry.setParameter("accvirtualid", accvirtualid);
			results = (Customeraccount) qry.list().get(0);
			log.trace("After get" + results.toString());
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
	
	public boolean getInActiveOrActiveListAccountByMobNoAndAccNoAndMaskedAccNo(String accNo, String mobileNo,
			String maskedAccNo) {
		log.trace("ACCNO: " + accNo + "MOBILENO:" + mobileNo);
		Session session = null;
		List<Customeraccount> customeraccount = null;
		boolean falg = false;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Customeraccount.class);
			cr.add(Restrictions.eq("accrefnumber", accNo));
			cr.add(Restrictions.eq("mobileno", mobileNo));
			cr.add(Restrictions.eq("maskedaccnumber", maskedAccNo));
			Criterion rest1 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			Criterion rest2 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.INACTIVE_ACCOUNT));
			cr.add(Restrictions.or(rest1, rest2));
			
			customeraccount = cr.list();
			if (0 != customeraccount.size()) {
				falg = true;
			}
			log.debug("return success from DB:" + customeraccount);
		} catch (
		
		Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.debug("return successfully with listAccount:" + customeraccount);
		return falg;
	}
	
	public List<Customeraccount> getListAccountByAccvirtualid(String accVPA) {
		log.trace("regVPA:" + accVPA);
		Session session = null;
		List<Customeraccount> listAccount = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Customeraccount.class);
			cr.add(Restrictions.eq("accvirtualid", accVPA));
			Criterion rest1 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			Criterion rest2 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.INACTIVE_ACCOUNT));
			cr.add(Restrictions.or(rest1, rest2));
			listAccount = cr.list();
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
		log.debug("Return successfuly with listAccount:" + listAccount);
		return listAccount;
	}
	
	public int getListAccountByRegVPAIdCount(Long regVPAId) {
		log.trace("regVPAId:" + regVPAId);
		Session session = null;
		BigInteger count = new BigInteger("0");
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(SEL_LIST_ACCOUNT_COUNT_BY_REGID);
			query.setParameter("regid", regVPAId);
			List list = query.list();
			if (list != null && list.size() == 1) {
				count = (BigInteger) list.get(0);
			}
			log.trace("count=[" + count + "+");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
		}
		return count.intValue();
	}
	
	public List<Customeraccount> getListAccountByRegVPAId(Long regVPAId) {
		log.trace("regVPAId:" + regVPAId);
		Session session = null;
		List<Customeraccount> listAccount = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Customeraccount.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regVPAId));
			/**
			 * As discussed with peeyush (08-02-2018)
			 */
			Criterion rest1 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.NORMAL_ACCOUNT));
			Criterion rest2 = Restrictions.and(Restrictions.eq(ConstantI.STATUS, ConstantI.INACTIVE_ACCOUNT));
			cr.add(Restrictions.or(rest1, rest2));
			
			listAccount = cr.list();
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
		log.debug("Return successfuly with listAccount:" + listAccount);
		return listAccount;
	}
	
	public List<Customeraccount> getNonPrimaryAccVPA(Long regId) {
		Session session = null;
		List<Customeraccount> custAccList = null;
		try {
			session = this.sessionFactory.openSession();
			final Criteria crAccDet = session.createCriteria(Customeraccount.class);
			crAccDet.add(Restrictions.eq(ConstantI.REGID, regId));
			crAccDet.add(Restrictions.eq(ConstantI.STATUS, 1));
			crAccDet.addOrder(Order.asc("accvirtualid"));
			custAccList = crAccDet.list();
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
		return custAccList;
	}
	
	public List<Customeraccount> getRecByMobileNo(String mobileNo) {
		
		Session session = null;
		List<Customeraccount> results = new ArrayList<Customeraccount>();
		try {
			session = this.sessionFactory.openSession();
			String sql = "select * from customeraccount where MOBILENO=:mobileNo and STATUS= 1 order by ACCADDEDDATE limit 1";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Customeraccount.class);
			query.setParameter("mobileNo", mobileNo);
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
	
	public List<Customeraccount> getRecByMobileNo(String mobileNo, Long regid, String vpa) {
		
		Session session = null;
		List<Customeraccount> results = new ArrayList<Customeraccount>();
		try {
			session = this.sessionFactory.openSession();
			String sql = "select * from customeraccount where MOBILENO=:mobileNo and REGID=:regid and ACCVIRTUALID=:vpa and STATUS= 1 and DEFACCOUNT=1";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Customeraccount.class);
			query.setParameter("mobileNo", mobileNo);
			query.setParameter("regid", regid);
			query.setParameter("vpa", vpa);
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
	
	public boolean saveListAccount(Customeraccount customeraccount) {
		log.trace("listaccount:" + customeraccount);
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(customeraccount);
			transaction.commit();
			flag = true;
			log.trace("After Commit" + customeraccount.toString());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("error in:" + s);
		} finally {
			session.close();
		}
		log.debug("return successfully with flag:" + flag);
		return flag;
	}
	
	
	public boolean saveListOfCustAccount(List<Customeraccount> list) {
		log.trace("listaccount:" + list);
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			for(Customeraccount acc:list) {
				session.saveOrUpdate(acc);	
			}
			transaction.commit();
			flag = true;
			log.info("Commit true");
		} catch (Exception e) {
			transaction.rollback();
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("error in:" + s);
			
		} finally {
			session.close();
		}
		log.debug("return successfully with flag:" + flag);
		return flag;
	}
	
	// Overloaded to insert/update data in customervpa table along with
	// customeraccount
	public boolean saveListAccount(Customeraccount customeraccount, CustomerVPA customerVpa) {
		log.trace("saveListAccount:");
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(customerVpa);
			session.saveOrUpdate(customeraccount);
			transaction.commit();
			flag = true;
		} catch (ConstraintViolationException e) {
			log.error("Duplicate VPA request ", e);
			transaction.rollback();
			customerVpa.setDuplicateVPA(true);
			return false;
		} catch (Exception e) {
			transaction.rollback();
			log.error(e.getMessage(), e);
			return false;
		} finally {
			session.close();
		}
		log.debug("return successfully with flag:" + flag);
		return flag;
	}
	
	public boolean setDefaultAccount(Customeraccount listaccount, Long regid, String accvirtualid) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query updateCurrentDefaultQry = session.createSQLQuery("update CUSTOMERACCOUNT set DEFACCOUNT = 2"
					+ " where REGID = :regid and ACCVIRTUALID = :accvirtualid and DEFACCOUNT = 1");
			updateCurrentDefaultQry.setParameter("regid", regid);
			updateCurrentDefaultQry.setParameter("accvirtualid", accvirtualid);
			int count = updateCurrentDefaultQry.executeUpdate();
			Criteria cr = session.createCriteria(Customeraccount.class);
			session.update(listaccount);
			transaction.commit();
			flag = true;
			log.trace("After Commit" + listaccount.toString());
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
	
	public boolean updateAccount(Customeraccount customeraccount) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(customeraccount);
			transaction.commit();
			flag = true;
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
	
	public boolean updateAccount(Long regId, String virtualId, String accRefNumber, String status) {
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			Query dergisterDefaultAccQuery = session.createSQLQuery(UPDATE_ACCOUNT_STATUS.toString());
			dergisterDefaultAccQuery.setParameter(ConstantI.STATUS, status);
			dergisterDefaultAccQuery.setParameter("accrefnumber", accRefNumber);
			dergisterDefaultAccQuery.setParameter(ConstantI.ACCVIRTUALID, virtualId);
			dergisterDefaultAccQuery.setParameter(ConstantI.REGID, regId);
			dergisterDefaultAccQuery.setParameter("defaccount", ConstantI.DEFAULT_ACC_Y);
			int count = dergisterDefaultAccQuery.executeUpdate();
			return count >= 1;
		} catch (Exception ex) {
			StringWriter s;
			ex.printStackTrace(new PrintWriter(s = new StringWriter()));
			ex.printStackTrace();
			log.error(s);
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public boolean updateVpa(Long regId, String virtualId, String status) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Query dergisterDefaultAccQuery = session.createSQLQuery("update customeraccount set status = :status "
					+ "where accvirtualid=:accvirtualid and regid=:regid");
			dergisterDefaultAccQuery.setParameter(ConstantI.STATUS, status);
			dergisterDefaultAccQuery.setParameter(ConstantI.ACCVIRTUALID, virtualId);
			dergisterDefaultAccQuery.setParameter(ConstantI.REGID, regId);
			int count = dergisterDefaultAccQuery.executeUpdate();
			flag = true;
		} catch (Exception ex) {
			StringWriter s;
			ex.printStackTrace(new PrintWriter(s = new StringWriter()));
			ex.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	
	public boolean updateNonPrimaryByCaidNotIn(Long regId,Integer caid,Integer status) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Query q = session.createQuery("update customeraccount set defaccount =:defaccount "
					+ "where regid=:regid and caid!=:caid and status = :status");
			q.setParameter("caid", caid);
			q.setParameter("defaccount", 0);
			q.setParameter(ConstantI.REGID, regId);
			q.setParameter("status", status);
			int count = q.executeUpdate();
			flag = true;
		} catch (Exception ex) {
			StringWriter s;
			ex.printStackTrace(new PrintWriter(s = new StringWriter()));
			ex.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	public boolean updatePrimaryByCaidandReg(Integer caid,Long regId) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Query q = session.createQuery("update customeraccount set defaccount =:defaccount "
					+ "where regid=:regid and caid!=:caid");
			q.setParameter("defaccount", 1);
			q.setParameter("caid", caid);
			q.setParameter(ConstantI.REGID, regId);
			int count = q.executeUpdate();
			flag = true;
		} catch (Exception ex) {
			StringWriter s;
			ex.printStackTrace(new PrintWriter(s = new StringWriter()));
			ex.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
}
