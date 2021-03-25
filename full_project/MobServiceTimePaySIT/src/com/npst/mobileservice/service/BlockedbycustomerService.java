package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.BlockedbycustomerHome;
import com.npst.mobileservice.object.BlockedbycustomerVO;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.Blockedbycustomer;

public class BlockedbycustomerService {
	private static final Logger log = Logger.getLogger(BlockedbycustomerService.class);
	BlockedbycustomerHome blockedbycustomerHome = null;

	public RespJson getBlockVpa(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ConstantI.FAILURE_STRING);
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == blockedbycustomerHome) {
				blockedbycustomerHome = new BlockedbycustomerHome();
			}
			List<Blockedbycustomer> blockedbycustomers = blockedbycustomerHome
					.selectByRegId(Integer.parseInt(reqJson.getRegId()));

			if (0 < blockedbycustomers.size()) {
				List<BlockedbycustomerVO> respBlockedbycustomers = new ArrayList<>();
				for (Blockedbycustomer blockedbycustomer : blockedbycustomers) {
					respBlockedbycustomers.add(new BlockedbycustomerVO(blockedbycustomer));
					respJson.setMsg(ConstantI.SUCCESS_STRING);
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				}
				respJson.setBlockedbycustomerVO(respBlockedbycustomers);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	public RespJson unblockVpa(ReqJson reqJson) {
		RespJson respJson = new RespJson();
		respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FAIL_UNBLOCK.getCode());
		respJson.setMsgId(ConstantI.MSGID_FAIL);
		try {
			respJson.setReqId(reqJson.getReqId());
			if (null == blockedbycustomerHome) {
				blockedbycustomerHome = new BlockedbycustomerHome();
			}
			List<Blockedbycustomer> blockedbycustomers = blockedbycustomerHome.select(reqJson.getVirtualId(),
					Integer.parseInt(reqJson.getRegId()));
			for (Blockedbycustomer blockedbycustomer : blockedbycustomers) {
				blockedbycustomer.setStatus(ConstantI.INACTIVE);
				if (blockedbycustomerHome.update(blockedbycustomer)) {
					respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_UNBLOCK.getCode());
					respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				}
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}
}
