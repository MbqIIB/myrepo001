package com.npst.upiserver.dao;

import java.util.Date;

import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.RespOtp;

public interface ReqRespOtpDao {

	void insertReqResp(ReqOtp reqOtp, RespOtp respOtp, Ack ack, Date reqDate);

	void insertReq(ReqOtp reqOtp, Ack ack);

	void updateResp(RespOtp respOtp);

}
