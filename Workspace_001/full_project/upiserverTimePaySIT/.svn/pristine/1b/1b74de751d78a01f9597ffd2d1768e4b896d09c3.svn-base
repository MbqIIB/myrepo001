package com.npst.upiserver.util;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.ReqHbt;
import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.npcischema.ReqListPsp;
import com.npst.upiserver.npcischema.ReqListVae;
import com.npst.upiserver.npcischema.ReqManageVae;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ReqPendingMsg;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespChkTxn;
import com.npst.upiserver.npcischema.RespHbt;
import com.npst.upiserver.npcischema.RespListAccPvd;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.npcischema.RespListPsp;
import com.npst.upiserver.npcischema.RespListVae;
import com.npst.upiserver.npcischema.RespManageVae;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.RespPendingMsg;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.RespTxnConfirmation;
import com.npst.upiserver.npcischema.RespValAdd;



public class UnMarshalUpi {
	static Logger				log							= LoggerFactory.getLogger(UnMarshalUpi.class.getName());
	public static JAXBContext	umReqAuthDetailsJaxB		= null;
	public static JAXBContext	umReqBalEnqJaxB				= null;
	public static JAXBContext	umReqChkTxnJaxB				= null;
	public static JAXBContext	umReqHbtJaxB				= null;
	public static JAXBContext	umReqListAccountJaxB		= null;
	public static JAXBContext	umReqListAccPvdJaxB			= null;
	public static JAXBContext	umReqListKeysJaxB			= null;
	public static JAXBContext	umReqListPspJaxB			= null;
	public static JAXBContext	umReqListVaeJaxB			= null;
	public static JAXBContext	umReqManageVaeJaxB			= null;
	public static JAXBContext	umReqOtpJaxB				= null;
	public static JAXBContext	umReqPayJaxB				= null;
	public static JAXBContext	umReqPendingMsgJaxB			= null;
	public static JAXBContext	umReqRegMobJaxB				= null;
	public static JAXBContext	umReqSetCreJaxB				= null;
	public static JAXBContext	umReqTxnConfirmationJaxB	= null;
	public static JAXBContext	umReqValAddJaxB				= null;
	public static JAXBContext	umRespBalEnqJaxB			= null;
	public static JAXBContext	umRespChkTxnJaxB			= null;
	public static JAXBContext	umRespHbtJaxB				= null;
	public static JAXBContext	umRespListAccountJaxB		= null;
	public static JAXBContext	umRespListAccPvdJaxB		= null;
	public static JAXBContext	umRespListKeysJaxB			= null;
	public static JAXBContext	umRespListPspJaxB			= null;
	public static JAXBContext	umRespListVaeJaxB			= null;
	public static JAXBContext	umRespManageVaeJaxB			= null;
	public static JAXBContext	umRespOtpJaxB				= null;
	public static JAXBContext	umRespPayJaxB				= null;
	public static JAXBContext	umRespPendingMsgJaxB		= null;
	public static JAXBContext	umRespRegMobJaxB			= null;
	public static JAXBContext	umRespSetCreJaxB			= null;
	public static JAXBContext	umRespTxnConfirmationJaxB	= null;
	public static JAXBContext	umRespValAddJaxB			= null;
	public static JAXBContext	umAckJaxB					= null;
	
	public static JAXBContext ackJaxB() {
		try {
			if (umAckJaxB == null) {
				umAckJaxB = JAXBContext.newInstance(Ack.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umAckJaxB;
	}
	
	public static JAXBContext reqAuthDetailJaxB() {
		try {
			if (umReqAuthDetailsJaxB == null) {
				umReqAuthDetailsJaxB = JAXBContext.newInstance(ReqAuthDetails.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqAuthDetailsJaxB;
	}
	
	public static JAXBContext reqBalEnqJaxB() {
		try {
			if (umReqBalEnqJaxB == null) {
				umReqBalEnqJaxB = JAXBContext.newInstance(ReqBalEnq.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqBalEnqJaxB;
	}

	public static JAXBContext reqChkTxnJaxB() {
		try {
			if (umReqChkTxnJaxB == null) {
				umReqChkTxnJaxB = JAXBContext.newInstance(ReqChkTxn.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqChkTxnJaxB;
	}

	public static JAXBContext reqHbtJaxB() {
		try {
			if (umReqHbtJaxB == null) {
				umReqHbtJaxB = JAXBContext.newInstance(ReqHbt.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqHbtJaxB;
	}

	public static JAXBContext reqListAccountJaxB() {
		try {
			if (umReqListAccountJaxB == null) {
				umReqListAccountJaxB = JAXBContext.newInstance(ReqListAccount.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqListAccountJaxB;
	}

	public static JAXBContext reqListAccPvdJaxB() {
		try {
			if (umReqListAccPvdJaxB == null) {
				umReqListAccPvdJaxB = JAXBContext.newInstance(ReqListAccPvd.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqListAccPvdJaxB;
	}

	public static JAXBContext reqListKeysJaxB() {
		try {
			if (umReqListKeysJaxB == null) {
				umReqListKeysJaxB = JAXBContext.newInstance(ReqListKeys.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqListKeysJaxB;
	}

	public static JAXBContext reqListPspJaxB() {
		try {
			if (umReqListPspJaxB == null) {
				umReqListPspJaxB = JAXBContext.newInstance(ReqListPsp.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqListPspJaxB;
	}

	public static JAXBContext reqListVaeJaxB() {
		try {
			if (umReqListVaeJaxB == null) {
				umReqListVaeJaxB = JAXBContext.newInstance(ReqListVae.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umReqListVaeJaxB;
	}

	public static JAXBContext reqManageVaeJaxB() {
		try {
			if (umReqManageVaeJaxB == null) {
				umReqManageVaeJaxB = JAXBContext.newInstance(ReqManageVae.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqManageVaeJaxB;
	}

	public static JAXBContext reqOtpJaxB() {
		try {
			if (umReqOtpJaxB == null) {
				umReqOtpJaxB = JAXBContext.newInstance(ReqOtp.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqOtpJaxB;
	}

	public static JAXBContext reqPayJaxB() {
		try {
			if (umReqPayJaxB == null) {
				umReqPayJaxB = JAXBContext.newInstance(ReqPay.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqPayJaxB;
	}

	public static JAXBContext reqPendingMsgJaxB() {
		try {
			if (umReqPendingMsgJaxB == null) {
				umReqPendingMsgJaxB = JAXBContext.newInstance(ReqPendingMsg.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqPendingMsgJaxB;
	}

	public static JAXBContext reqRegMobJaxB() {
		try {
			if (umReqRegMobJaxB == null) {
				umReqRegMobJaxB = JAXBContext.newInstance(ReqRegMob.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqRegMobJaxB;
	}

	public static JAXBContext reqSetCreJaxB() {
		try {
			if (umReqSetCreJaxB == null) {
				umReqSetCreJaxB = JAXBContext.newInstance(ReqSetCre.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqSetCreJaxB;
	}

	public static JAXBContext reqTxnConfirmationJaxB() {
		try {
			if (umReqTxnConfirmationJaxB == null) {
				umReqTxnConfirmationJaxB = JAXBContext.newInstance(ReqTxnConfirmation.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqTxnConfirmationJaxB;
	}

	public static JAXBContext reqValAddJaxB() {
		try {
			if (umReqValAddJaxB == null) {
				umReqValAddJaxB = JAXBContext.newInstance(ReqValAdd.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umReqValAddJaxB;
	}

	public static JAXBContext respBalEnqJaxB() {
		try {
			if (umRespBalEnqJaxB == null) {
				umRespBalEnqJaxB = JAXBContext.newInstance(RespBalEnq.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umRespBalEnqJaxB;
	}

	public static JAXBContext respChkTxnJaxB() {
		try {
			if (umRespChkTxnJaxB == null) {
				umRespChkTxnJaxB = JAXBContext.newInstance(RespChkTxn.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umRespChkTxnJaxB;
	}

	public static JAXBContext respHbtJaxB() {
		try {
			if (umRespHbtJaxB == null) {
				umRespHbtJaxB = JAXBContext.newInstance(RespHbt.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umRespHbtJaxB;
	}

	public static JAXBContext respListAccountJaxB() {
		try {
			if (umRespListAccountJaxB == null) {
				umRespListAccountJaxB = JAXBContext.newInstance(RespListAccount.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			//
		}
		return umRespListAccountJaxB;
	}

	public static JAXBContext respListAccPvdJaxB() {
		try {
			if (umRespListAccPvdJaxB == null) {
				umRespListAccPvdJaxB = JAXBContext.newInstance(RespListAccPvd.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespListAccPvdJaxB;
	}

	public static JAXBContext respListKeysJaxB() {
		try {
			if (umRespListKeysJaxB == null) {
				umRespListKeysJaxB = JAXBContext.newInstance(RespListKeys.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespListKeysJaxB;
	}

	public static JAXBContext respListPspJaxB() {
		try {
			if (umRespListPspJaxB == null) {
				umRespListPspJaxB = JAXBContext.newInstance(RespListPsp.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespListPspJaxB;
	}

	public static JAXBContext respListVaeJaxB() {
		try {
			if (umRespListVaeJaxB == null) {
				umRespListVaeJaxB = JAXBContext.newInstance(RespListVae.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespListVaeJaxB;
	}

	public static JAXBContext respManageVaeJaxB() {
		try {
			if (umRespManageVaeJaxB == null) {
				umRespManageVaeJaxB = JAXBContext.newInstance(RespManageVae.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespManageVaeJaxB;
	}

	public static JAXBContext respOtpJaxB() {
		try {
			if (umRespOtpJaxB == null) {
				umRespOtpJaxB = JAXBContext.newInstance(RespOtp.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespOtpJaxB;
	}

	public static JAXBContext respPayJaxB() {
		try {
			if (umRespPayJaxB == null) {
				umRespPayJaxB = JAXBContext.newInstance(RespPay.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespPayJaxB;
	}

	public static JAXBContext respPendingMsgJaxB() {
		try {
			if (umRespPendingMsgJaxB == null) {
				umRespPendingMsgJaxB = JAXBContext.newInstance(RespPendingMsg.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespPendingMsgJaxB;
	}

	public static JAXBContext respRegMobJaxB() {
		try {
			if (umRespRegMobJaxB == null) {
				umRespRegMobJaxB = JAXBContext.newInstance(RespRegMob.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespRegMobJaxB;
	}

	public static JAXBContext respSetCreJaxB() {
		try {
			if (umRespSetCreJaxB == null) {
				umRespSetCreJaxB = JAXBContext.newInstance(RespSetCre.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespSetCreJaxB;
	}

	public static JAXBContext respTxnConfirmationJaxB() {
		try {
			if (umRespTxnConfirmationJaxB == null) {
				umRespTxnConfirmationJaxB = JAXBContext.newInstance(RespTxnConfirmation.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespTxnConfirmationJaxB;
	}

	public static JAXBContext respValAddJaxB() {
		try {
			if (umRespValAddJaxB == null) {
				umRespValAddJaxB = JAXBContext.newInstance(RespValAdd.class);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			
		}
		return umRespValAddJaxB;
	}

}
