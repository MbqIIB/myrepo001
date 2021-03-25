package com.npst.mobileservice.dao;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;

public class UpiPinHome {
	private static final Logger log = Logger.getLogger(UpiPinHome.class);
	private SessionFactory sessionFactory = getSessionFactory();

	public void delete(String mobileNo) {
		RespJson respJson = new RespJson();
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("delete from upipin where mobile_no = :mobileNo");
			query.setParameter("mobileNo", Integer.parseInt(mobileNo));

			int result = query.executeUpdate();

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
}
