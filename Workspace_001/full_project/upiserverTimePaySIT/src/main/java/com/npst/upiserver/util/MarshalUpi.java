package com.npst.upiserver.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.ReqHbt;
import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.npcischema.ReqListPsp;
import com.npst.upiserver.npcischema.ReqListVae;
import com.npst.upiserver.npcischema.ReqManageVae;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ReqPendingMsg;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespChkTxn;
import com.npst.upiserver.npcischema.RespHbt;
import com.npst.upiserver.npcischema.RespListAccPvd;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.npcischema.RespListPsp;
import com.npst.upiserver.npcischema.RespListVae;
import com.npst.upiserver.npcischema.RespManageVae;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.RespMandateConfirmation;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.RespPendingMsg;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.RespTxnConfirmation;
import com.npst.upiserver.npcischema.RespValAdd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshalUpi {

	private static final Logger log = LoggerFactory.getLogger(MarshalUpi.class);
	static JAXBContext jAXBContextRespBal = null;
	static JAXBContext jAXBContextAuthDetails = null;
	static JAXBContext jAXBContextReqBalEnq = null;
	static JAXBContext jAXBContextReqChkTxn = null;
	static JAXBContext jAXBContextReqHbt = null;
	static JAXBContext jAXBContextReqListAccount = null;
	static JAXBContext jAXBContextReqListAccPvd = null;
	static JAXBContext jAXBContextReqListKeys = null;
	static JAXBContext jAXBContextReqListPsp = null;
	static JAXBContext jAXBContextReqListVae = null;
	static JAXBContext jAXBContextReqManageVae = null;
	static JAXBContext jAXBContextReqOtp = null;
	static JAXBContext jAXBContextReqPay = null;
	static JAXBContext jAXBContextReqPendingMsg = null;
	static JAXBContext jAXBContextReqRegMob = null;
	static JAXBContext jAXBContextReqSetCre = null;
	static JAXBContext jAXBContextReqTxnConfirmation = null;
	static JAXBContext jAXBContextReqValAdd = null;
	static JAXBContext jAXBContextRespAuthDetails = null;
	static JAXBContext jAXBContextRespChkTxn = null;
	static JAXBContext jAXBContextRespHbt = null;
	static JAXBContext jAXBContextRespListAccount = null;
	static JAXBContext jAXBContextRespListAccPvd = null;
	static JAXBContext jAXBContextRespListKeys = null;
	static JAXBContext jAXBContextRespListPsp = null;
	static JAXBContext jAXBContextRespListVae = null;
	static JAXBContext jAXBContextRespManageVae = null;
	static JAXBContext jAXBContextRespOtp = null;
	static JAXBContext jAXBContextRespPay = null;
	static JAXBContext jAXBContextRespPendingMsg = null;
	static JAXBContext jAXBContextRespRegMob = null;
	static JAXBContext jAXBContextRespSetCre = null;
	static JAXBContext jAXBContextRespTxnConfirmation = null;
	static JAXBContext jAXBContextRespValAdd = null;

	static JAXBContext jAXBContextRespMandateConfirmation = null;

	static JAXBContext jAXBContextRespMandate = null;

	static JAXBContext jAXBContextMandateSign = null;

	private static JAXBContext jAXBContextReqMandate;

	public static StringWriter marshal(final ReqAuthDetails reqAuthDetails) {
		// log.trace(JsonMan.getJSONStr(reqAuthDetails));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqAuthDetails = null;
			if (null == jAXBContextAuthDetails) {
				jAXBContextAuthDetails = JAXBContext.newInstance(ReqAuthDetails.class);
			}
			mReqAuthDetails = jAXBContextAuthDetails.createMarshaller();
			mReqAuthDetails.marshal(reqAuthDetails, sw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqAuthMandate reqAuthMandate) {
		// log.trace(JsonMan.getJSONStr(reqAuthMandate));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqAuthDetails = null;
			if (null == jAXBContextAuthDetails) {
				jAXBContextAuthDetails = JAXBContext.newInstance(ReqAuthMandate.class);
			}
			mReqAuthDetails = jAXBContextAuthDetails.createMarshaller();
			mReqAuthDetails.marshal(reqAuthMandate, sw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : {}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqBalEnq reqBalEnq) {
		// log.trace(JsonMan.getJSONStr(reqBalEnq));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqBalEnq = null;
			if (null == jAXBContextReqBalEnq) {
				jAXBContextReqBalEnq = JAXBContext.newInstance(ReqBalEnq.class);
			}
			mReqBalEnq = jAXBContextReqBalEnq.createMarshaller();
			mReqBalEnq.marshal(reqBalEnq, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqChkTxn reqChkTxn) {
		// log.trace(JsonMan.getJSONStr(reqChkTxn));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqChkTxn = null;
			if (null == jAXBContextReqChkTxn) {
				jAXBContextReqChkTxn = JAXBContext.newInstance(ReqChkTxn.class);
			}
			mReqChkTxn = jAXBContextReqChkTxn.createMarshaller();
			mReqChkTxn.marshal(reqChkTxn, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqHbt reqHbt) {
		// log.trace(JsonMan.getJSONStr(reqHbt));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqHbt = null;
			if (null == jAXBContextReqHbt) {
				jAXBContextReqHbt = JAXBContext.newInstance(ReqHbt.class);
			}
			mReqHbt = jAXBContextReqHbt.createMarshaller();
			mReqHbt.marshal(reqHbt, sw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqListAccount reqListAccount) {
		// log.trace(JsonMan.getJSONStr(reqListAccount));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqListAccount = null;
			if (null == jAXBContextReqListAccount) {
				jAXBContextReqListAccount = JAXBContext.newInstance(ReqListAccount.class);
			}
			mReqListAccount = jAXBContextReqListAccount.createMarshaller();
			mReqListAccount.marshal(reqListAccount, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqListAccPvd reqListAccPvd) {
		// log.trace(JsonMan.getJSONStr(reqListAccPvd));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqListAccPvd = null;
			if (null == jAXBContextReqListAccPvd) {
				jAXBContextReqListAccPvd = JAXBContext.newInstance(ReqListAccPvd.class);
			}
			mReqListAccPvd = jAXBContextReqListAccPvd.createMarshaller();
			mReqListAccPvd.marshal(reqListAccPvd, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqListKeys reqListKeys) {
		// log.info("Inside the marshling of request list keys:>>>",
		// JsonMan.getJSONStr(reqListKeys));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqListKeys = null;
			if (null == jAXBContextReqListKeys) {
				jAXBContextReqListKeys = JAXBContext.newInstance(ReqListKeys.class);
			}
			mReqListKeys = jAXBContextReqListKeys.createMarshaller();
			mReqListKeys.marshal(reqListKeys, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqListPsp reqListPsp) {
		// log.trace(JsonMan.getJSONStr(reqListPsp));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqListPsp = null;
			if (null == jAXBContextReqListPsp) {
				jAXBContextReqListPsp = JAXBContext.newInstance(ReqListPsp.class);
			}
			mReqListPsp = jAXBContextReqListPsp.createMarshaller();
			mReqListPsp.marshal(reqListPsp, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqListVae reqListVae) {
		// log.trace(JsonMan.getJSONStr(reqListVae));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqListVae = null;
			if (null == jAXBContextReqListVae) {
				jAXBContextReqListVae = JAXBContext.newInstance(ReqListVae.class);
			}
			mReqListVae = jAXBContextReqListVae.createMarshaller();
			mReqListVae.marshal(reqListVae, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqManageVae reqManageVae) {
		// log.trace(JsonMan.getJSONStr(reqManageVae));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqManageVae = null;
			if (null == jAXBContextReqManageVae) {
				jAXBContextReqManageVae = JAXBContext.newInstance(ReqManageVae.class);
			}
			mReqManageVae = jAXBContextReqManageVae.createMarshaller();
			mReqManageVae.marshal(reqManageVae, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqMandate reqMandate) {
		// log.trace(JsonMan.getJSONStr(reqMandate));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqBalEnq = null;
			if (null == jAXBContextReqMandate) {
				jAXBContextReqMandate = JAXBContext.newInstance(ReqMandate.class);
			}
			mReqBalEnq = jAXBContextReqMandate.createMarshaller();
			mReqBalEnq.marshal(reqMandate, sw);

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error("Error : {}", s);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqOtp reqOtp) {
		// log.trace(JsonMan.getJSONStr(reqOtp));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqOtp = null;
			if (null == jAXBContextReqOtp) {
				jAXBContextReqOtp = JAXBContext.newInstance(ReqOtp.class);
			}
			mReqOtp = jAXBContextReqOtp.createMarshaller();
			mReqOtp.marshal(reqOtp, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqPay reqPay) {
		// log.trace(JsonMan.getJSONStr(reqPay));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqPay = null;
			if (null == jAXBContextReqPay) {
				jAXBContextReqPay = JAXBContext.newInstance(ReqPay.class);
			}
			mReqPay = jAXBContextReqPay.createMarshaller();
			mReqPay.marshal(reqPay, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqPendingMsg reqPendingMsg) {
		// log.trace(JsonMan.getJSONStr(reqPendingMsg));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqPendingMsg = null;
			if (null == jAXBContextReqPendingMsg) {
				jAXBContextReqPendingMsg = JAXBContext.newInstance(ReqPendingMsg.class);
			}
			mReqPendingMsg = jAXBContextReqPendingMsg.createMarshaller();
			mReqPendingMsg.marshal(reqPendingMsg, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqRegMob reqRegMob) {
		// log.trace(JsonMan.getJSONStr(reqRegMob));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqRegMob = null;
			if (null == jAXBContextReqRegMob) {
				jAXBContextReqRegMob = JAXBContext.newInstance(ReqRegMob.class);
			}
			mReqRegMob = jAXBContextReqRegMob.createMarshaller();
			mReqRegMob.marshal(reqRegMob, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqSetCre reqSetCre) {
		// log.trace(JsonMan.getJSONStr(reqSetCre));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqSetCre = null;
			if (null == jAXBContextReqSetCre) {
				jAXBContextReqSetCre = JAXBContext.newInstance(ReqSetCre.class);
			}
			mReqSetCre = jAXBContextReqSetCre.createMarshaller();
			mReqSetCre.marshal(reqSetCre, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqTxnConfirmation reqTxnConfirmation) {
		// log.trace(JsonMan.getJSONStr(reqTxnConfirmation));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqTxnConfirmation = null;
			if (null == jAXBContextReqTxnConfirmation) {
				jAXBContextReqTxnConfirmation = JAXBContext.newInstance(ReqTxnConfirmation.class);
			}
			mReqTxnConfirmation = jAXBContextReqTxnConfirmation.createMarshaller();
			mReqTxnConfirmation.marshal(reqTxnConfirmation, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final ReqValAdd reqValAdd) {
		// log.trace(JsonMan.getJSONStr(reqValAdd));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqValAdd = null;
			if (null == jAXBContextReqValAdd) {
				jAXBContextReqValAdd = JAXBContext.newInstance(ReqValAdd.class);
			}
			mReqValAdd = jAXBContextReqValAdd.createMarshaller();
			mReqValAdd.marshal(reqValAdd, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespAuthDetails respAuthDetails) {
		// log.trace(JsonMan.getJSONStr(respAuthDetails));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespAuthDetails = null;
			if (null == jAXBContextRespAuthDetails) {
				jAXBContextRespAuthDetails = JAXBContext.newInstance(RespAuthDetails.class);
			}
			mRespAuthDetails = jAXBContextRespAuthDetails.createMarshaller();
			mRespAuthDetails.marshal(respAuthDetails, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : {}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespAuthMandate respAuthMandate) {
		// log.trace(JsonMan.getJSONStr(respAuthMandate));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mReqAuthDetails = null;
			if (null == jAXBContextAuthDetails) {
				jAXBContextAuthDetails = JAXBContext.newInstance(RespAuthMandate.class);
			}
			mReqAuthDetails = jAXBContextAuthDetails.createMarshaller();
			mReqAuthDetails.marshal(respAuthMandate, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(RespBalEnq respBalEnq) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespBalEnq = JAXBContext.newInstance(RespBalEnq.class).createMarshaller();
			mRespBalEnq.marshal(respBalEnq, sw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespChkTxn respChkTxn) {
		// log.trace(JsonMan.getJSONStr(respChkTxn));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespChkTxn = null;
			if (null == jAXBContextRespChkTxn) {
				jAXBContextRespChkTxn = JAXBContext.newInstance(RespChkTxn.class);
			}
			mRespChkTxn = jAXBContextRespChkTxn.createMarshaller();
			mRespChkTxn.marshal(respChkTxn, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
		}
		return sw;
	}

	public static StringWriter marshal(final RespHbt respHbt) {
		// log.trace(JsonMan.getJSONStr(respHbt));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespHbt = null;
			if (null == jAXBContextRespHbt) {
				jAXBContextRespHbt = JAXBContext.newInstance(RespHbt.class);
			}
			mRespHbt = jAXBContextRespHbt.createMarshaller();
			mRespHbt.marshal(respHbt, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(RespListAccount respListAccount) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespListAccount = JAXBContext.newInstance(RespListAccount.class).createMarshaller();
			mRespListAccount.marshal(respListAccount, sw);
			// Constant.mRespListAccount.marshal(respListAccount, sw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);

		}
		return sw;
	}

	public static StringWriter marshal(final RespListAccPvd respListAccPvd) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespListAccPvd = null;
			if (null == jAXBContextRespListAccPvd) {
				jAXBContextRespListAccPvd = JAXBContext.newInstance(RespListAccPvd.class);
			}
			mRespListAccPvd = jAXBContextRespListAccPvd.createMarshaller();
			mRespListAccPvd.marshal(respListAccPvd, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespListKeys respListKeys) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespListKeys = null;
			if (null == jAXBContextRespListKeys) {
				jAXBContextRespListKeys = JAXBContext.newInstance(RespListKeys.class);
			}
			mRespListKeys = jAXBContextRespListKeys.createMarshaller();
			mRespListKeys.marshal(respListKeys, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespListPsp respListPsp) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespListPsp = null;
			if (null == jAXBContextRespListPsp) {
				jAXBContextRespListPsp = JAXBContext.newInstance(RespListPsp.class);
			}
			mRespListPsp = jAXBContextRespListPsp.createMarshaller();
			mRespListPsp.marshal(respListPsp, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespListVae respListVae) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespListVae = null;
			if (null == jAXBContextRespListVae) {
				jAXBContextRespListVae = JAXBContext.newInstance(RespListVae.class);
			}
			mRespListVae = jAXBContextRespListVae.createMarshaller();
			mRespListVae.marshal(respListVae, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespManageVae respManageVae) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespManageVae = null;
			if (null == jAXBContextRespManageVae) {
				jAXBContextRespManageVae = JAXBContext.newInstance(RespManageVae.class);
			}
			mRespManageVae = jAXBContextRespManageVae.createMarshaller();
			mRespManageVae.marshal(respManageVae, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespMandate respMandate) {
		// log.trace(JsonMan.getJSONStr(respMandate));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespMandate = null;
			if (null == jAXBContextRespMandate) {
				jAXBContextRespMandate = JAXBContext.newInstance(RespMandate.class);
			}
			mRespMandate = jAXBContextRespMandate.createMarshaller();
			mRespMandate.marshal(respMandate, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error {}");
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespMandateConfirmation respMandateConfirmation) {
		// log.trace(JsonMan.getJSONStr(respMandateConfirmation));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespMandateConfirmation = null;
			if (null == jAXBContextRespMandateConfirmation) {
				jAXBContextRespMandateConfirmation = JAXBContext.newInstance(RespMandateConfirmation.class);
			}
			mRespMandateConfirmation = jAXBContextRespMandateConfirmation.createMarshaller();
			mRespMandateConfirmation.marshal(respMandateConfirmation, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error {}",e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespOtp respOtp) {
		// log.trace(JsonMan.getJSONStr(respOtp));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespOtp = null;
			if (null == jAXBContextRespOtp) {
				jAXBContextRespOtp = JAXBContext.newInstance(RespOtp.class);
			}
			mRespOtp = jAXBContextRespOtp.createMarshaller();
			mRespOtp.marshal(respOtp, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespPay respPay) {
		// log.trace(JsonMan.getJSONStr(respPay));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespPay = null;
			if (null == jAXBContextRespPay) {
				jAXBContextRespPay = JAXBContext.newInstance(RespPay.class);
			}
			mRespPay = jAXBContextRespPay.createMarshaller();
			mRespPay.marshal(respPay, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespPendingMsg respPendingMsg) {
		// log.trace(JsonMan.getJSONStr(respPendingMsg));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespPendingMsg = null;
			if (null == jAXBContextRespPendingMsg) {
				jAXBContextRespPendingMsg = JAXBContext.newInstance(RespPendingMsg.class);
			}
			mRespPendingMsg = jAXBContextRespPendingMsg.createMarshaller();
			mRespPendingMsg.marshal(respPendingMsg, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespRegMob respRegMob) {
		// log.trace(JsonMan.getJSONStr(respRegMob));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespRegMob = null;
			if (null == jAXBContextRespRegMob) {
				jAXBContextRespRegMob = JAXBContext.newInstance(RespRegMob.class);
			}
			mRespRegMob = jAXBContextRespRegMob.createMarshaller();
			mRespRegMob.marshal(respRegMob, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespSetCre respSetCre) {
		// log.trace(JsonMan.getJSONStr(respSetCre));
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespSetCre = null;
			if (null == jAXBContextRespSetCre) {
				jAXBContextRespSetCre = JAXBContext.newInstance(RespSetCre.class);
			}
			mRespSetCre = jAXBContextRespSetCre.createMarshaller();
			mRespSetCre.marshal(respSetCre, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespTxnConfirmation respTxnConfirmation) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespTxnConfirmation = null;
			if (null == jAXBContextRespTxnConfirmation) {
				jAXBContextRespTxnConfirmation = JAXBContext.newInstance(RespTxnConfirmation.class);
			}
			mRespTxnConfirmation = jAXBContextRespTxnConfirmation.createMarshaller();
			mRespTxnConfirmation.marshal(respTxnConfirmation, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static StringWriter marshal(final RespValAdd respValAdd) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespValAdd = null;
			if (null == jAXBContextRespValAdd) {
				jAXBContextRespValAdd = JAXBContext.newInstance(RespValAdd.class);
			}
			mRespValAdd = jAXBContextRespValAdd.createMarshaller();
			mRespValAdd.marshal(respValAdd, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error  {}",e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

	public static Object marshal(MandateType mandateSign) {
		StringWriter sw = new StringWriter();
		try {
			Marshaller mRespMandateSign = null;
			if (null == jAXBContextMandateSign) {
				jAXBContextMandateSign = JAXBContext.newInstance(MandateType.class);
			}
			mRespMandateSign = jAXBContextMandateSign.createMarshaller();
			mRespMandateSign.marshal(mandateSign, sw);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MarshalUpi.class);
		}
		return sw;
	}

}
