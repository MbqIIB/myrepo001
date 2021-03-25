package com.npst.middleware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.AccountVerification;
import com.npst.middleware.service.BalEnq;
import com.npst.middleware.service.ChkTxn;
import com.npst.middleware.service.Credit;
import com.npst.middleware.service.Debit;
import com.npst.middleware.service.LienAmountRequestService;
import com.npst.middleware.service.ListAccount;
import com.npst.middleware.service.Otp;
import com.npst.middleware.service.PinCheckService;
import com.npst.middleware.service.ReSetRegMob;
import com.npst.middleware.service.ReqRegMob;
import com.npst.middleware.service.Reversal;
import com.npst.middleware.service.SetCre;
import com.npst.middleware.util.URLConstants;

@RestController
@RequestMapping(URLConstants.BASE_URL)
public class UpiApi
{
	private static final Logger LOG = LoggerFactory.getLogger(UpiApi.class);

	@Autowired
	public BalEnq balEnq;
	@Autowired
	public ListAccount listAccount;
	@Autowired
	public Otp otp;
	@Autowired
	public ReqRegMob reqRegMob;
	@Autowired
	public SetCre setCre;
	@Autowired
	public Credit credit;
	@Autowired
	public Debit debit;
	@Autowired
	public ChkTxn chkTxn;
	@Autowired
	public ReSetRegMob reSetRegMob;
	@Autowired
	public Reversal reversal;
	
	@Autowired
	public PinCheckService pinCheckService;
	
	@Autowired
	public AccountVerification	accountVerification;
	
	@Autowired
	public LienAmountRequestService lienAmontReqService;

	@RequestMapping(value = URLConstants.BAL_ENQ, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp balenq(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting balenq controller {} ", reqResp);
		return balEnq.fetch(reqResp);
	}

	@RequestMapping(value = URLConstants.CHK_TXN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp chktxn(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting chktxn controller {} ", reqResp);
		return chkTxn.fetch(reqResp);
	}

	@RequestMapping(value = URLConstants.CREDIT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp credit(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting credit controller {} ", reqResp);
		return credit.perform(reqResp);
	}

	@RequestMapping(value = URLConstants.DEBIT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp debit(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting debit controller  {} ", reqResp);
		return debit.perform(reqResp);
	}

	@RequestMapping(value = URLConstants.LIST_ACCOUNT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp listaccount(@RequestBody final ReqResp reqResp)
	{
		LOG.debug("value of isUPI {}",reqResp.getIsUPI2());
		LOG.trace("Starting listaccount controller  {} ", reqResp);
		return listAccount.fetch(reqResp);
	}

	@RequestMapping(value = URLConstants.OTP, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp otp(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting otp controller  {} ", reqResp);
		return otp.send(reqResp);
	}

	@RequestMapping(value = URLConstants.REQ_REG_MOB, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp reqregmob(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting reqregmob controller  {} ", reqResp);
		return reqRegMob.perform(reqResp);
	}

	@RequestMapping(value = URLConstants.RESET_REG_MOB, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp resetregmob(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting resetregmob controller  {} ", reqResp);
		return reqRegMob.perform(reqResp);
	}

	@RequestMapping(value = URLConstants.REVERSAL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp reversal(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting reversal controller  {} ", reqResp);
		return reversal.perform(reqResp);
	}

	@RequestMapping(value = URLConstants.SET_CRE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp setcre(@RequestBody final ReqResp reqResp)
	{
		LOG.trace("Starting setcre controller  {} ", reqResp);
		return setCre.perform(reqResp);
	}
	
	@RequestMapping(value = URLConstants.VAL_ADD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp accountVerification(@RequestBody final ReqResp reqResp) {
		LOG.trace("Starting Account Verification controller {}" ,reqResp );
		return accountVerification.perform(reqResp);
	}
	
	@RequestMapping(value = URLConstants.MANDATE_BLOCK, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp blockAmount(@RequestBody
	final ReqResp reqResp) {
		LOG.trace("Starting Amount Block /Modify / Revoke controller {}" ,reqResp );
		return lienAmontReqService.perform(reqResp);
	}
	
	
	@RequestMapping(value = URLConstants.PV, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ReqResp pinValidation(@RequestBody final ReqResp reqResp) {
		LOG.trace("Starting pinValidation controller {}", reqResp);
		return pinCheckService.perform(reqResp);
	}

	
}
