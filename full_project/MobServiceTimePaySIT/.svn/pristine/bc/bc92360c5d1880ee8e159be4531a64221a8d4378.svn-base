package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.MasterConfig;

/**
 * Home object for domain model class Onusmobreqrespjson.
 *
 * @see Onusmobreqvaladd.Onusmobreqrespjson
 * @author Hibernate Tools
 */

public class CustomerTxnsHome {
	private static final Logger log = Logger.getLogger(CustomerTxnsHome.class);
	// private final static StringBuilder qryCustomerTxnByDateWithStatus = new
	// StringBuilder();
	// private final static StringBuilder qryCustomerTxnByDateWithoutStatus =
	// new
	// StringBuilder();
	// private final static StringBuilder qryCustomerTxnByDateWithVpa = new
	// StringBuilder();
	private final static StringBuilder selectTxnHistory = new StringBuilder();
	// private final static StringBuilder qryTxnHistory = new StringBuilder();
	private final static String orderBy = " order by c.reqDate desc LIMIT :historyTo,:lastData ";
	private static String pageQ = " and (c.reqDate between :sDate and :eDate) and c.amount >= :frmAmnt and amount <= :toAmnt ";
	private static String status = " and c.status = :txnStatus ";

	private final static String vpaQ = " and (payeeVpa=:vpaStr or payerVpa=:vpaStr)";
	static {
		selectTxnHistory.append("SELECT c.CUSTOMERTXNSID, c.amount, c.collectExpiry, c.customerHistory, ");
		selectTxnHistory.append(" c.errorcode,c.payeeAcType,c.payeeAccIFSC,c.payeeAccNo, ");
		selectTxnHistory.append(" c.payeeMMID,c.payeeMobileNo,c.payeeName,c.payeeVpa,c.payerAcType, ");
		selectTxnHistory.append(" c.payerAccIFSC,c.payerAccNo,c.payerMMID,c.payerMobileNo,c.payerName, ");
		selectTxnHistory.append(" c.payerVpa,c.regId,c.reqDate,c.respDate,c.status,c.txnId,c.txnNote, ");
		selectTxnHistory.append(" c.txnType,c.txncustRef,c.payeeType,ct.BANKADJREFNO,ct.ADJAMOUNT, ");
		selectTxnHistory.append(" ct.APITYPE,ct.complaintDATE,ct.CUSTREF,ct.FLAG,ct.MOBILENO,ct.NAME, ");
		selectTxnHistory.append(" ct.pspStatus,ct.REASONCD,ct.reasonCode,ct.REMARK,ct.COMPSTATUS, ");
		selectTxnHistory.append(" ct.TXNID,ct.TXNTIME,ct.TXNDATE,ct.VIRTUALID,ct.disputeType ");
		selectTxnHistory.append(" FROM customertxns AS c ");
		selectTxnHistory.append(" LEFT JOIN complaint AS ct ON ct.txnId = c.TXNID ");
		selectTxnHistory.append(" where c.regId=:regId and amount>0 and not (c.txnType=2 and c.status <>2) ");
		// selectTxnHistory.append(" and c.txnType in (:txnType)");
		// qryTxnHistory.append(selectTxnHistory.toString());
		// qryTxnHistory.append(orderBy);

		// qryCustomerTxnByDateWithStatus.append();
		// qryCustomerTxnByDateWithStatus.append();
		// qryCustomerTxnByDateWithStatus.append();
		// qryCustomerTxnByDateWithStatus.append();
		// qryCustomerTxnByDateWithoutStatus.append(selectTxnHistory.toString());
		// qryCustomerTxnByDateWithoutStatus.append(pageQ);
		// qryCustomerTxnByDateWithoutStatus.append(orderBy);

		// qryCustomerTxnByDateWithVpa.append(selectTxnHistory.toString());
		// qryCustomerTxnByDateWithVpa.append(" and (c.reqDate between :sDate
		// and
		// :eDate) and c.amount >= :frmAmnt");
		// qryCustomerTxnByDateWithVpa.append(" and (payerVpa=:vpa or
		// payeeVpa=:vpa) ");
		// qryCustomerTxnByDateWithVpa.append(" and amount <= :toAmnt " +
		// orderBy);

	}
	private SessionFactory sessionFactory = getSessionFactory();

	public MasterConfig findStatus(String code) {
		Session session = null;
		List<MasterConfig> results = null;
		try {
			session = this.sessionFactory.openSession();
			final String sql = "SELECT *\r\n" + "FROM masterdetails\r\n" + "where code=:code";
			log.debug(sql);
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(MasterConfig.class);
			query.setParameter("code", code);
			results = query.list();
			return results.get(0);

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		} finally {
			session.close();
		}
	}

	protected SessionFactory getSessionFactory() {
		try {
			// return HibernateListener.getSessionFactory();
			return (SessionFactory) new InitialContext().lookup("HibernateListener");
		} catch (final Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public List<Object[]> history(final String regvpaId, final int historyFrom, final int historyTo, int dataTo,
			String txnType) {
		Session session = null;
		List<Object[]> results = null;
		try {
			if (null == txnType || "".equals(txnType)) {
				txnType = "21,22,1,2";
			}
			String lastQ = Util.calQus(txnType);
			session = this.sessionFactory.openSession();
			String fullQ = selectTxnHistory.toString() + " and c.txnType in (" + lastQ + ") " + orderBy;
			log.debug(fullQ);
			SQLQuery query = session.createSQLQuery(fullQ);
			query.setParameter("regId", regvpaId);
			query.setParameter("historyTo", historyTo);
			query.setParameter("lastData", dataTo);
			// query.setParameter("txnType", txnType);
			String[] txnTypes = txnType.split(",");
			for (int i = 0; i < txnTypes.length; i++) {
				query.setParameter("txnType" + i, txnTypes[i]);
			}
			results = query.list();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		} finally {
			session.close();
		}
		return results;
	}

	public List<Object[]> historyByDate(String regvpaId, Date sDate, Date eDate, String frmAmnt, String toAmnt,
			String txnStatus, int historyTo, int dataTo, String txnType, String vpa) {
		Session session = null;
		log.trace("-----------data to process for history by date----------");
		log.trace("regvpaId - " + regvpaId + " - sDate - " + sDate + " - eDate - " + eDate + " - frmAmnt - " + frmAmnt
				+ " - toAmnt - " + toAmnt + " - txnStatus - " + txnStatus);
		List<Object[]> results = new ArrayList<>();
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = null;
			String sql = null;
			if (null == txnType || "".equals(txnType)) {
				txnType = "21,22,1,2";
			}
			String lastQ = Util.calQus(txnType);
			if (txnStatus != null) {
				sql = selectTxnHistory.toString() + " and c.txnType in (" + lastQ + ") " + pageQ + " " + status;
				if (!(vpa == null || "".equals(vpa))) {
					sql = sql + " " + vpaQ;
				}

				sql = sql + orderBy;
				log.debug(sql);
				query = session.createSQLQuery(sql);
				query.setParameter("txnStatus", txnStatus);
			} else {
				sql = selectTxnHistory.toString() + " and c.txnType in ( " + lastQ + ") " + pageQ;
				if (!(vpa == null || "".equals(vpa))) {
					sql = sql + " " + vpaQ;
				}
				sql = sql + orderBy;
				log.debug(sql);
				query = session.createSQLQuery(sql);
			}
			if (!(vpa == null || "".equals(vpa))) {
				query.setParameter("vpaStr", vpa);
			}
			String[] txnTypes = txnType.split(",");
			for (int i = 0; i < txnTypes.length; i++) {
				query.setParameter("txnType" + i, txnTypes[i]);
			}
			// log.trace("sql query :: " + sql);
			query.setParameter("regId", regvpaId);
			query.setParameter("sDate", sDate);
			query.setParameter("eDate", eDate);
			query.setParameter("frmAmnt", Double.parseDouble(frmAmnt));
			query.setParameter("toAmnt", Double.parseDouble(toAmnt));
			query.setParameter("historyTo", historyTo);
			query.setParameter("lastData", dataTo);
			// query.setParameter("txnType", txnType);
			log.trace("query to get history by date :: " + query);
			results = query.list();
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");

		} finally {
			session.close();
		}
		return results;
	}
}
