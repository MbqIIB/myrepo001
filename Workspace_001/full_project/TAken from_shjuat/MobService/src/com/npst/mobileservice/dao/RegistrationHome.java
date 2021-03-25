package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.service.AcqTxnLimitService;
import com.npst.mobileservice.service.DeviceRegLimitService;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.HibernateListener;
import com.npst.upi.hor.CustomerVPA;
import com.npst.upi.hor.Custvpa;
import com.npst.upi.hor.Feedback;
import com.npst.upi.hor.MasterConfig;
import com.npst.upi.hor.Registration;
import com.npst.upi.hor.Silentsms;

public class RegistrationHome {
	private static final Logger	log								= Logger.getLogger(RegistrationHome.class);
	private SessionFactory		sessionFactory					= getSessionFactory();
	DateFormat					dateFormat						= new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	private static String		updateDeviceIdNIMEI				= "update registration C set C.DEVICEIMEI = :DEVICEIMEI, C.DEVICEID= :DEVICEID ,C.UPDATEDDT= :UPDATEDDT  where C.REGID= :regid";
	private static String		UPDATE_DEVICE_ID_REGISTRATION	= "update registration C set C.DEVICEID = null , C.UPDATEDDT= :UPDATEDDT  where C.DEVICEID= :deviceId and C.STATUS!= :regStatus ";
	private static String GET_CUSTOMERACTIVE_ON_MOBILE_STATUS = "select * from registration where MOBNO=:mobileNo and STATUS !=:status";
	private static AcqTxnLimitService acqTxnLimitService;
	public RespJson checkRegApp(ReqJson reqJson) {
		Session session = null;
		RespJson respJson = new RespJson();
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAIL_REGISTRATION_PROCESS.getCode());
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.MOBNO, reqJson.getMobileNo()));
			List<Registration> results = cr.list();
			if (0 < results.size()) {
				for (Registration regvpacheck : results) {
					//log.info("Registration Status is:"+regvpacheck.getStatus());
					if (ConstantI.ACTIVE_REGVPA == regvpacheck.getStatus()) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_ALL_READY_ACTIVE_MSG.getCode());
						return respJson;
					} else if (ConstantI.LOGIN_FAIL_BLOCK == regvpacheck.getStatus()) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_LOGIN_FAIL_BLOCK_MSG.getCode());
						return respJson;
					} else if (ConstantI.PSP_BLOCK == regvpacheck.getStatus()) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_PSP_BLOCK_MSG.getCode());
						return respJson;
					} else if (ConstantI.TEMP_BLOCK == regvpacheck.getStatus()) {
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_TEMP_BLOCK_MSG.getCode());
						return respJson;
					} else {
						respJson.setMsgId(ConstantI.MSGID_SUCCESS);
						respJson.setMsg(ConstantI.SUCCESS_STRING);
					}
				}
			} else {
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);
			}
		} catch (Exception e) {
			log.error(e);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			session.close();
		}
		return respJson;
	}
	
	public boolean checkVPA(String vpa) {
		Session session = null;
		List<Registration> registrationList = null;
		boolean isVpaExits = true;
		try {
			session = this.sessionFactory.openSession();
			Criteria crAccDet = session.createCriteria(Registration.class);
			crAccDet.add(Restrictions.eq(ConstantI.VPA, vpa));
			registrationList = crAccDet.list();
			if (null != registrationList && 0 < registrationList.size()) {
				isVpaExits = true;
			} else {
				isVpaExits = false;
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isVpaExits;
	}
	
	public Registration getUserById(Long regId) {
		log.trace("regvpaId:" + regId);
		Session session = null;
		Registration registration = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regId));
			registration = (Registration) cr.uniqueResult();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.debug("return successfully with regVPA:" + registration);
		return registration;
	}
	
	public Registration getUserByIdAndMobNo(Long regVPAId, String mobNo) {
		log.trace("regvpaId:" + regVPAId + "mobNo :" + mobNo);
		Session session = null;
		Registration regVPA = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regVPAId));
			cr.add(Restrictions.eq(ConstantI.MOBNO, mobNo));
			regVPA = (Registration) cr.uniqueResult();
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
		log.debug("return successfully with regVPA:" + regVPA);
		return regVPA;
	}
	
	public Registration getUserByIdAndMobNoAndPin(Long regVPAId, String mobNo, String pin) {
		log.trace("regvpaId[" + regVPAId + "] and mobNo :[" + mobNo + "]" + " pin: " + pin);
		Session session = null;
		Registration regVPA = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regVPAId));
			cr.add(Restrictions.eq(ConstantI.MOBNO, mobNo));
			cr.add(Restrictions.eq(ConstantI.LOGINPIN, pin));
			regVPA = (Registration) cr.uniqueResult();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.debug("return successfully with regVPA:" + regVPA);
		return regVPA;
	}
	
	public List<Registration> getUserByMobNo(String mobNo) {
		log.trace("mobNo :" + mobNo);
		Session session = null;
		List<Registration> regVPA = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.MOBNO, mobNo));
			
			regVPA = cr.list();
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
		return regVPA;
	}
	
	public List<Registration> getUserByMobNoNew(String mobNo) {
		log.trace("mobNo :" + mobNo);
		Session session = null;
		List<Registration> regVPA = null;
		try {
			log.info("before execute query get active Customer inside getUserByMobNoNew");
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(GET_CUSTOMERACTIVE_ON_MOBILE_STATUS);
			query.addEntity(Registration.class);
			query.setParameter("mobileNo", mobNo);
			query.setParameter("status", ConstantI.DEREGISTER);
			regVPA = query.list();
			log.info("after execute query get active Customer inside getUserByMobNoNew");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return regVPA;
	}
	
	public List<Registration> getActiveUserByMobNo(String mobNo) {
		log.trace("mobNo :" + mobNo);
		Session session = null;
		List<Registration> regVPA = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.MOBNO, mobNo));
			cr.add(Restrictions.eq("status", ConstantI.ACTIVE_REGVPA));
			regVPA = cr.list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return regVPA;
	}
	
	public RespJson registerUser(Registration registration) {
		return registerUser(registration, null);
	}
	
	/*public static void main(String[] args) {
		RegistrationHome hm=new RegistrationHome();
		hm.updateDeviceIdIMEI(6,"NTg3OTIzZWNiYTFjMWMzYw==","ODk5MTExMDAwMDA1NjEzMDA2ODM=");
		//
	}*/
	
	// Overloaded to insert/update data in customervpa table
	public RespJson registerUser(Registration registration, CustomerVPA customerVpa) {
		RespJson respJson = new RespJson();
		Session session = null;
		Transaction transaction = null;
		try {
			log.trace("save or update registration record for entity [" + registration.getDeviceid().trim() + "] and VPA ["
					+ registration.getMobno() + " ]");
			if(!DeviceRegLimitService.getInstance().isDeviceRegLmtAllowed(registration.getDeviceid().trim(), registration.getMobno())) {
				//System.out.println("REG LIM EXCEED");
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg("MAX DEVICE REG LIMIT EXCEEDED");//DeviceRegLimitService.getInstance().getLimitErrorMsg()
				//System.out.println(respJson.toString());
				return respJson;
			}
			
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			log.trace("checking registration with device id [" + registration.getDeviceid().trim() + "] and mobileNo ["+ registration.getMobno() + " ]");
			
			// Updating all device ids as null if already exists previously
			int updated = session.createSQLQuery(UPDATE_DEVICE_ID_REGISTRATION)
					.setTimestamp(ConstantI.UPDATEDDT, new Date()).setString("deviceId", registration.getDeviceid().trim())
					.setInteger("regStatus", ConstantI.DEREGISTER).executeUpdate();
			
			log.info("Updated [" + updated + "] rows in registration with device id same  before user register");
			
			session.save(registration);
			if (customerVpa != null) {
				Custvpa cVpa = new Custvpa();
				cVpa.setRegId(registration.getRegid());
				cVpa.setRegVpa(registration.getDefvpa());
				customerVpa.setCustVpa(cVpa);
				session.saveOrUpdate(customerVpa);
			}
			transaction.commit();
			
			if (acqTxnLimitService == null) {
				acqTxnLimitService = AcqTxnLimitService.getInstance();
			}
			acqTxnLimitService.saveNewUser(registration.getMobno());
			DeviceRegLimitService.getInstance().updatePlusCount(registration.getDeviceid().trim());
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REGSUCCESS.getCode());
			respJson.setRegId(String.valueOf(registration.getRegid()));
			respJson.setServerToken(registration.getServertoken());
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			if(session!=null) {
				session.close();	
			}
		}
		return respJson;
	}
	
	
	// Overloaded to insert/update data in customervpa table
	public RespJson registerMerchantUser(Registration registration) {
		RespJson respJson = new RespJson();
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			log.trace("checking registration with device id [" + registration.getDeviceid().trim() + "] and mobileNo ["
					+ registration.getMobno() + " ]");
			
			// Updating all device ids as null if already exists previously
//			int updated = session.createSQLQuery(UPDATE_DEVICE_ID_REGISTRATION)
//					.setTimestamp(ConstantI.UPDATEDDT, new Date()).setString("deviceId", registration.getDeviceid())
//					.setInteger("regStatus", ConstantI.DEREGISTER).executeUpdate();
//			
//			log.info("Updated [" + updated + "] rows in registration with device id same  before user register");
			
			session.save(registration);
			CustomerVPA custVpa = new CustomerVPA();
			Custvpa cVpa = new Custvpa();
			cVpa.setRegId(registration.getRegid());
			cVpa.setRegVpa(registration.getDefvpa());
			custVpa.setCustVpa(cVpa);
			custVpa.setCreateDate(new Date());
			session.saveOrUpdate(custVpa);
			transaction.commit();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REGSUCCESS.getCode());
			respJson.setRegId(String.valueOf(registration.getRegid()));
			respJson.setServerToken(registration.getServertoken());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			if(transaction!=null) {
				transaction.rollback();
			}
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			if(session!=null) {
				session.close();	
			}
		}
		return respJson;
	}
	public boolean saveGCMTokenInRegVPA(Long regvpaId, String mobileNo, String gcmToken) {
		
		log.trace("regvpaId[" + regvpaId + "] and mobileNo [" + mobileNo + "] and gcmToken :[" + gcmToken + "]");
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			Criterion rest1 = Restrictions.and(Restrictions.eq(ConstantI.REGID, regvpaId));
			Criterion rest2 = Restrictions.and(Restrictions.eq(ConstantI.MOBNO, mobileNo));
			cr.add(Restrictions.or(rest1, rest2));
			List<Registration> results = cr.list();
			log.debug("Regvpa Records count" + results.size());
			try {
				if (results.size() > 0) {
					for (Object element : results) {
						Transaction transaction = session.beginTransaction();
						Registration regVpa = (Registration) element;
						if (ConstantI.ACTIVE_REGVPA == regVpa.getStatus()) {
							regVpa.setGcmtoken(gcmToken);
							session.update(regVpa);
							transaction.commit();
						}
						flag = true;
					}
				}
			} catch (Exception e) {
				StringWriter s;
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				e.printStackTrace();
				log.error(s);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			session.close();
		}
		log.debug("return successfully with flag:" + flag);
		return flag;
	}
	
	public boolean updateRegVPA(Registration registration) {
		log.trace("Regvpa :" + registration);
		Session session = null;
		boolean flag = false;
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(registration);
			transaction.commit();
			flag = true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("error in:" + s);
		} finally {
			session.close();
		}
		return flag;
	}
	
	public List<Registration> validateServerToken(ReqJson reqJson) {
		log.trace(reqJson);
		Session session = null;
		List<Registration> results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, Long.parseLong(reqJson.getRegId())));
			cr.add(Restrictions.eq(ConstantI.MOBNO, reqJson.getMobileNo()));
			results = cr.list();
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
	
	public Registration getUserByDeviceId(String deviceId) {
		log.trace(deviceId);
		Session session = null;
		Registration results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq("deviceid", deviceId.trim()));
			cr.add(Restrictions.eq("status", ConstantI.ACTIVE_REGVPA));
			cr.addOrder(Order.desc("createddt"));
			cr.setMaxResults(1);
			results = (Registration) cr.uniqueResult();
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
	
	public Registration getUserByDeviceId(String regId, String deviceId) {
		log.trace("RegId="+regId);
		log.trace("DeviceId"+deviceId);
		Session session = null;
		Registration results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq("regid", Long.parseLong(regId)));
			cr.add(Restrictions.eq("deviceid", deviceId.trim()));
			cr.add(Restrictions.eq("status", ConstantI.ACTIVE_REGVPA));
			cr.addOrder(Order.desc("createddt"));
			cr.setMaxResults(1);
			results = (Registration) cr.uniqueResult();
			System.out.println(results);
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	public Registration getUserByDeviceIdNMob(String deviceId, String mobNo) {
		log.trace(deviceId);
		Session session = null;
		Registration results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq("deviceid", deviceId.trim()));
			cr.add(Restrictions.eq("mobno", mobNo));
			cr.add(Restrictions.eq("status", ConstantI.ACTIVE_REGVPA));
			cr.addOrder(Order.desc("createddt"));
			cr.setMaxResults(1);
			results = (Registration) cr.uniqueResult();
		} catch (Exception e) {
			log.error(e);
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
			log.error(e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
	
	public void delete(long regId) {
		RespJson respJson = new RespJson();
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("delete from registration where mobno = :id");
			query.setParameter("id", regId);
			
			int result = query.executeUpdate();
			
		} catch (Exception e) {
			log.error(e);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			session.close();
		}
	}
	
	public Registration findByRegId(Long regId) {
		log.trace(regId);
		Session session = null;
		Registration results = null;
		try {
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Registration.class);
			cr.add(Restrictions.eq(ConstantI.REGID, regId));
			results = (Registration) cr.uniqueResult();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	public RespJson saveFeedback(Feedback feedback) {
		RespJson respJson = new RespJson();
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(feedback);
			transaction.commit();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_REGSUCCESS.getCode());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		} finally {
			session.close();
		}
		return respJson;
	}
	
	public List<Feedback> findByRegIdAndSDate(Long regId, Date date) {
		log.trace(regId);
		Session session = null;
		List<Feedback> results = null;
		SQLQuery query = null;
		Long count = 0L;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM feedback WHERE regId=:regId and feedback_date=:feedback_date";
			query = session.createSQLQuery(sql);
			query.addEntity(Feedback.class);
			query.setParameter("regId", regId);
			query.setParameter("feedback_date", date);
			results = query.list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	public List<MasterConfig> getScreenPathCode(String path) {
		log.trace(path);
		Session session = null;
		List<MasterConfig> results = null;
		SQLQuery query = null;
		try {
			session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM masterdetails WHERE value=:value";
			query = session.createSQLQuery(sql);
			query.addEntity(MasterConfig.class);
			query.setParameter("value", path);
			results = query.list();
		} catch (Exception e) {
			log.error(e);
		} finally {
			session.close();
		}
		return results;
	}
	
	public boolean updateDeviceIdIMEI(final long regId, final String deviceId, final String deviceIMEI) {
		Session session = null;
		Transaction tx = null;
		try {
			session = this.sessionFactory.openSession();
			tx = session.beginTransaction();
			
			int updated = session.createSQLQuery(UPDATE_DEVICE_ID_REGISTRATION)
					.setTimestamp(ConstantI.UPDATEDDT, new Date()).setString("deviceId", deviceId.trim())
					.setInteger("regStatus", ConstantI.DEREGISTER).executeUpdate();
			
			log.info(" Updated [" + updated + "] records in registration with device id same  before update device imei");
			
			updated = session.createSQLQuery(updateDeviceIdNIMEI).setString(ConstantI.DEVICE_ID, deviceId.trim())
					.setString(ConstantI.DEVICE_IMEI, deviceIMEI).setTimestamp(ConstantI.UPDATEDDT, new Date())
					.setLong(ConstantI.REGID, regId).executeUpdate();
			tx.commit();
			return updated >= 1;
		} catch (Exception e) {
			log.error(e);
			tx.rollback();
			return false;
		} finally {
			session.close();
		}
	}
	
}
