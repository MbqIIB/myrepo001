package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.DecryptionUtility;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.upi.hor.Beneficiary;

public class BeneficiaryDao {
	private static final Logger log = Logger.getLogger(RegistrationHome.class);
	private static final String QRY_BENE_BY_ID_AND_STATUS = "select * from beneficiary where beneid=:beneid and status=:status";
	private static final String QRY_BENE_BY_AADHAR = "select * from beneficiary where regid=:regid and payeeadharno=:payeeadharno";
	private static final String QRY_BENE_BY_MMID = "select * from beneficiary where regid=:regid and payeemobilen=:payeemobilen AND payeemmid=:payeemmid";
	private static final String QRY_BENE_BY_VPA = "select * from beneficiary where regid=:regid and payeeaddr=:payeeaddr";
	private static final String QRY_BENE_BY_ACCOUNT = "select * from beneficiary where regid=:regid and payeeaccno=:payeeaccno";
	private SessionFactory sessionFactory = getSessionFactory();

	// Changed from criteria to sql (Himanshu Gusain)
	public RespJson addBeneficiaryByAccountNumber(ReqJson reqJson) {

		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		RespJson respJson = new RespJson();
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String sql = "select * from beneficiary where regid=:regid and status=:status and payeeaccno=:payeeaccno";

			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Beneficiary.class);
			query.setParameter("regid", Integer.parseInt(reqJson.getRegId()));
			query.setParameter("status", ConstantI.ACTIVE);
			query.setParameter("payeeaccno", DecryptionUtility.encrypt(reqJson.getPayeeaccno()));
			List<Beneficiary> results = query.list();

			if (results.size() == 1) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
				return respJson;
			}
			Beneficiary beneficiary = new Beneficiary();

			beneficiary.setRegid(Integer.parseInt(reqJson.getRegId()));
			beneficiary.setPayeeaccno(DecryptionUtility.encrypt(reqJson.getPayeeaccno()));
			beneficiary.setPayeeifsc(DecryptionUtility.encrypt(reqJson.getIfsc()));

			beneficiary.setStatus(ConstantI.ACTIVE);
			Calendar calendar = Calendar.getInstance();

			java.util.Date now = calendar.getTime();

			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

			beneficiary.setAddeddt(currentTimestamp);
			beneficiary.setUpdated(currentTimestamp);

			session.save(beneficiary);
			transaction.commit();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());

			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.trace("return successfully with respJson:" + respJson);
		return respJson;

	}

	// Changed from criteria to sql (Himanshu Gusain)
	public RespJson addBeneficiaryByMobileNumber(ReqJson reqJson) {

		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		RespJson respJson = new RespJson();

		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String sql = "select * from beneficiary where regid=:regid and payeemobilen=:payeemobilen";
			// session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Beneficiary.class);
			query.setParameter("regid", Integer.parseInt(reqJson.getRegId()));
			query.setParameter("payeemobilen", reqJson.getMobileNo());
			List<Beneficiary> results = query.list();
			if (results.size() == 1) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ErrorCode.AcqErrorCode.ALREADY_ADDED_BNF.getCode());
				return respJson;
			}
			Beneficiary beneficiary = new Beneficiary();

			beneficiary.setRegid(Integer.parseInt(reqJson.getRegId()));

			beneficiary.setPayeemobilen(reqJson.getMobileNo());
			beneficiary.setPayeemmid(reqJson.getMmid());

			beneficiary.setStatus(ConstantI.ACTIVE);
			Calendar calendar = Calendar.getInstance();

			java.util.Date now = calendar.getTime();

			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

			beneficiary.setAddeddt(currentTimestamp);
			beneficiary.setUpdated(currentTimestamp);

			session.save(beneficiary);
			transaction.commit();
			respJson.setMsgId(ConstantI.MSGID_SUCCESS);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());

			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.trace("return successfully with respJson:" + respJson);
		return respJson;

	}

	// Changed from criteria to sql (Himanshu Gusain)
	public Beneficiary getBenefbyBeneIdAndStatus(ReqJson reqJson) {
		Session session = null;
		RespJson respJson = new RespJson();
		Beneficiary beneficiary = null;
		try {

			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(QRY_BENE_BY_ID_AND_STATUS);
			query.addEntity(Beneficiary.class);
			query.setParameter("beneid", Integer.parseInt(reqJson.getBeneid()));
			query.setParameter("status", ConstantI.ACTIVE);
			beneficiary = (Beneficiary) query.uniqueResult();
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.trace("return successfully with respJson:" + respJson);
		return beneficiary;
	}

	// Changed from criteria to sql (Himanshu Gusain)
	public List<Beneficiary> getBeneficiary(ReqJson reqJson) {
		// List<BeneficiaryDto> beneficiaryList = new
		// ArrayList<BeneficiaryDto>();
		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		RespJson respJson = new RespJson();
		List<Beneficiary> results = null;
		try {
			session = this.sessionFactory.openSession();
			// Transaction transaction = session.beginTransaction();
			String sql = "select * from beneficiary where regid=:regid";
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Beneficiary.class);
			query.setParameter("regid", Integer.parseInt(reqJson.getRegId()));
			results = query.list();
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
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

	// Changed from criteria to sql (Himanshu Gusain)
	public List<Beneficiary> getBeneficiary(ReqJson reqJson, String type) {
		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		List<Beneficiary> results = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = null;
			if ("VPA".equalsIgnoreCase(type)) {
				query = session.createSQLQuery(QRY_BENE_BY_VPA);
				query.setParameter("payeeaddr", reqJson.getPayeeaddr());
			} else if ("ACCIFSC".equalsIgnoreCase(type)) {
				query = session.createSQLQuery(QRY_BENE_BY_ACCOUNT);
				query.setParameter("payeeaccno", DecryptionUtility.encrypt(reqJson.getPayeeaccno()));
			} else if ("MOBMMID".equalsIgnoreCase(type)) {
				query = session.createSQLQuery(QRY_BENE_BY_MMID);
				query.setParameter("payeemobilen", DecryptionUtility.encrypt(reqJson.getPayeemobilen()));
				query.setParameter("payeemmid", DecryptionUtility.encrypt(reqJson.getPayeemmid()));
			} else {
				query = session.createSQLQuery(QRY_BENE_BY_AADHAR);
				query.setParameter("payeeadharno", DecryptionUtility.encrypt(reqJson.getPayeeadharno()));
			}
			query.addEntity(Beneficiary.class);
			query.setParameter("regid", Integer.parseInt(reqJson.getRegId()));
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
		log.trace("return successfully with respJson:" + results);
		return results;

	}

	// Changed from criteria to sql (Himanshu Gusain)
	public Beneficiary getBeneficiaryByBeneIdAndRegId(ReqJson reqJson) {
		// List<BeneficiaryDto> beneficiaryList = new
		// ArrayList<BeneficiaryDto>();
		log.info("entering into the method getBeneficiary() of BeneficiaryDao");
		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		RespJson respJson = new RespJson();
		Beneficiary results = null;
		try {

			String sql = "select * from beneficiary where beneid=:beneid and regid=:regid";
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Beneficiary.class);
			query.setParameter("beneid", Integer.parseInt(reqJson.getBeneid()));
			query.setParameter("regid", Integer.parseInt(reqJson.getRegId()));
			results = (Beneficiary) query.uniqueResult();
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
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

	public boolean insertBenef(Beneficiary beneficiary) {
		log.trace("Beneficiary[" + beneficiary + "]");
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(beneficiary);
			transaction.commit();
			return true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}

	}

	public boolean isNotNullOrEmpty(final Object object) {
		if (object != null) {
			return true;
		}
		return false;
	}

	// Changed from criteria to sql (Himanshu Gusain)
	public RespJson updateBeneficiary(ReqJson reqJson) {
		log.trace("ReqJson[" + reqJson + "]");
		Session session = null;
		RespJson respJson = new RespJson();

		try {
			String sql = "select * from beneficiary where beneid=:beneid and regid=:regid";
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Beneficiary.class);
			query.setParameter("beneid", Integer.parseInt(reqJson.getBeneid()));
			query.setParameter("status", ConstantI.ACTIVE);
			List<Beneficiary> results = query.list();
			if (results.size() == 0) {
				respJson.setMsgId(ConstantI.MSGID_FAIL);
				respJson.setMsg(ConstantI.FAILURE_STRING);
				return respJson;
			}
			for (Beneficiary beneficiary : results) {
				if (reqJson.getPayeeaddr() != null) {
					beneficiary.setPayeeaddr(reqJson.getPayeeaddr());
				}

				else if (reqJson.getPayeeaccno() != null && reqJson.getIfsc() != null) {

					beneficiary.setPayeeaccno(reqJson.getPayeeaccno());
					beneficiary.setPayeeifsc(reqJson.getPayeeifsc());

				} else if (reqJson.getMobileNo() != null) {

					beneficiary.setPayeemobilen(DecryptionUtility.encrypt(reqJson.getMobileNo()));
					beneficiary.setPayeeifsc(reqJson.getPayeeifsc());
				} else if (reqJson.getPayeeadharno() != null && reqJson.getPayeeiin() != null) {

					beneficiary.setPayeeadharno(DecryptionUtility.decrypt(reqJson.getPayeeadharno()));

				} else {

					respJson.setMsgId(ConstantI.MSGID_FAIL);
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
					return respJson;

				}

				beneficiary.setBeneid(Integer.parseInt(reqJson.getBeneid()));

				beneficiary.setRegid(Integer.parseInt(reqJson.getRegId()));

				beneficiary.setPayeename(beneficiary.getPayeename());
				Calendar calendar = Calendar.getInstance();

				java.util.Date now = calendar.getTime();

				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

				beneficiary.setAddeddt(currentTimestamp);

				beneficiary.setUpdated(currentTimestamp);

				beneficiary.setStatus(beneficiary.getStatus());
				session.update(beneficiary);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ConstantI.SUCCESS_STRING);

				transaction.commit();

			}
		} catch (Exception e) {
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ConstantI.SUCCESS_STRING);
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return respJson;
	}

	public boolean updateBenf(Beneficiary beneficiary) {
		log.trace("Beneficiary[" + beneficiary + "]");
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(beneficiary);
			transaction.commit();
			return true;
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}

	}

}
