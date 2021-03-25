package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.ReqRespVaePspKeysDao;
import com.npst.upiserver.entity.ListPspEntity;
import com.npst.upiserver.entity.ListPspKeysEntity;
import com.npst.upiserver.entity.ListVaeEntity;
import com.npst.upiserver.entity.ReqRespVaePspKeysEntity;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.npcischema.ReqListPsp;
import com.npst.upiserver.npcischema.ReqListVae;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.npcischema.RespListPsp;
import com.npst.upiserver.npcischema.RespListVae;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.ListPspKeysRepo;
import com.npst.upiserver.repo.ListPspRepo;
import com.npst.upiserver.repo.ListVaeRepo;
import com.npst.upiserver.repo.ReqRespVaePspKeysRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespVaePspKeysDaoImpl implements ReqRespVaePspKeysDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespVaePspKeysDaoImpl.class);
	
	@Autowired
	private ReqRespVaePspKeysRepository reqRespVaePspKeysRepo;
	@Autowired
	private ListPspKeysRepo listPspKeysRepo;

	@Autowired
	private ListVaeRepo listVaeRepo;

	@Autowired
	private ListPspRepo listPspRepo;

	@Override
	public void insertReq(ReqListPsp reqListPsp) {
		try {
			ReqRespVaePspKeysEntity en = new ReqRespVaePspKeysEntity();
			en.setReqDate(new Date());
			en.setReqHeadMsgId(reqListPsp.getHead().getMsgId());
			en.setReqHeadOrgId(reqListPsp.getHead().getOrgId());
			en.setReqHeadTs(reqListPsp.getHead().getTs());
			en.setTxnId(reqListPsp.getTxn().getId());
			en.setTxnTs(reqListPsp.getTxn().getTs());
			en.setTxnType(reqListPsp.getTxn().getType().toString());
			reqRespVaePspKeysRepo.save(en);
		} catch (Exception e) {
			log.error("error while insert reqListPsp {}", e);
		}
	}


	@Override
	public void insertReq(ReqListKeys reqListPsp) {
		try {
			ReqRespVaePspKeysEntity en = new ReqRespVaePspKeysEntity();
			en.setReqDate(new Date());
			en.setReqHeadMsgId(reqListPsp.getHead().getMsgId());
			en.setReqHeadOrgId(reqListPsp.getHead().getOrgId());
			en.setReqHeadTs(reqListPsp.getHead().getTs());
			en.setTxnId(reqListPsp.getTxn().getId());
			en.setTxnTs(reqListPsp.getTxn().getTs());
			en.setTxnType(reqListPsp.getTxn().getType().toString());
			reqRespVaePspKeysRepo.save(en);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespVaePspKeysDaoImpl.class);
		}

	}

	@Override
	public void updateListPSPKeys(RespListKeys respListKeys) {
		try {
			Date respDate = new Date();
			ReqRespVaePspKeysEntity en = null;
			if (respListKeys.getResp().getReqMsgId() != null) {
				List<ReqRespVaePspKeysEntity> rlist = reqRespVaePspKeysRepo
						.findByReqHeadMsgId(respListKeys.getResp().getReqMsgId());
				if (rlist.size() > 0) {
					en = rlist.get(0);
					if (rlist.size() > 1) {
						log.warn("more than one ReqRespVaePspKeysEntity records found for ReqHeadMsgId={}",
								respListKeys.getResp().getReqMsgId());
					}
				}
			}
			if (en == null) {
				en = new ReqRespVaePspKeysEntity();
				en.setTxnType(respListKeys.getTxn().getType().toString());
				en.setTxnId(respListKeys.getTxn().getId());
				en.setTxnTs(respListKeys.getTxn().getTs());
			}
			en.setRespDate(respDate);
			en.setRespHeadMsgId(respListKeys.getHead().getMsgId());
			en.setRespHeadOrgId(respListKeys.getHead().getOrgId());
			en.setRespHeadTs(respListKeys.getHead().getTs());
			en.setResult(respListKeys.getResp().getResult());
			reqRespVaePspKeysRepo.save(en);
			long parantId = en.getId();
			log.info("inside psplist proccess");
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respListKeys.getResp().getResult())) {
				List<ListPspKeysEntity> list = new ArrayList<ListPspKeysEntity>();
				respListKeys.getKeyList().getKey().stream().forEach(r -> {
					ListPspKeysEntity ob = new ListPspKeysEntity();
					ob.setPspOrgId(r.getCode());
					ob.setKeyCode(r.getCode());
					ob.setKeyKi(r.getKi());
					ob.setKeyType(r.getType());
					ob.setKeyValue(r.getKeyValue().toString());
					ob.setRecordedDate(respDate);
					ob.setOwner(r.getOwner());
					ob.setIdParant(parantId);
					list.add(ob);
				});
				listPspKeysRepo.saveAll(list);
			}
		} catch (Exception e) {
			log.error("error while insert reqListPsp {}", e);
			ErrorLog.sendError(e, ReqRespVaePspKeysDaoImpl.class);
		}
		
	}
	@Override
	public void updateListVae(RespListVae respListVae) {
		// TODO Auto-generated method stub
		try {
			Date respDate = new Date();
			if (respListVae != null && respListVae.getHead() != null && respListVae.getResp() != null) {
				ReqRespVaePspKeysEntity en = null;
				List<ReqRespVaePspKeysEntity> enList = reqRespVaePspKeysRepo
						.findByReqHeadMsgId(respListVae.getResp().getReqMsgId());
				if (enList.size() == 0) {
					en = enList.get(0);
				}
				if (en == null) {
					en = new ReqRespVaePspKeysEntity();
					en.setTxnType(respListVae.getTxn().getType().toString());
					en.setTxnId(respListVae.getTxn().getId());
					en.setTxnTs(respListVae.getTxn().getTs());
				}
				en.setRespDate(respDate);
				en.setRespHeadMsgId(respListVae.getHead().getMsgId());
				en.setRespHeadOrgId(respListVae.getHead().getOrgId());
				en.setRespHeadTs(respListVae.getHead().getTs());
				en.setResult(respListVae.getResp().getResult());
				reqRespVaePspKeysRepo.save(en);
				long parantId = en.getId();
				if (ResultType.SUCCESS.toString().equalsIgnoreCase(respListVae.getResp().getResult())) {
					List<ListVaeEntity> listResult = new ArrayList<ListVaeEntity>();
					respListVae.getVaeList().getVae().stream().forEach(result -> {
						ListVaeEntity ob = new ListVaeEntity();
						ob.setIdParant(parantId);
						if (result.getKey() != null) {
							ob.setKeyCode(result.getKey().getCode());
							ob.setKeyKi(result.getKey().getKi());
							ob.setKeyType(result.getKey().getType());
							ob.setKeyValue(result.getKey().getKeyValue());
						}
						ob.setAddr(result.getAddr());
						ob.setName(result.getName());
						ob.setRecordedDate(respDate);
						ob.setUrl(result.getUrl());
						ob.setLogo(result.getLogo());
						ob.setIdParant(parantId);
						listResult.add(ob);
					});
					listVaeRepo.saveAll(listResult);
				}

			}
		} catch (Exception e) {
			log.error("errorin processing RespListVae {}", e);
			ErrorLog.sendError(e, ReqRespVaePspKeysDaoImpl.class);
		}
	}

	@Override
	public void updateRespListPsp(RespListPsp respListPsp) {
		// TODO Auto-generated method stub
		try {
			Date respDate = new Date();
			ReqRespVaePspKeysEntity en = null;
			List<ReqRespVaePspKeysEntity> enList = reqRespVaePspKeysRepo
					.findByReqHeadMsgId(respListPsp.getResp().getReqMsgId());
			if (enList.size() == 1) {
				en = enList.get(0);
			}
			if (en == null) {
				en = new ReqRespVaePspKeysEntity();
				en.setTxnType(respListPsp.getTxn().getType().toString());
				en.setTxnId(respListPsp.getTxn().getId());
				en.setTxnTs(respListPsp.getTxn().getTs());
			}
			en.setRespDate(respDate);
			en.setRespHeadMsgId(respListPsp.getHead().getMsgId());
			en.setRespHeadOrgId(respListPsp.getHead().getOrgId());
			en.setRespHeadTs(respListPsp.getHead().getTs());
			en.setResult(respListPsp.getResp().getResult());
			reqRespVaePspKeysRepo.save(en);
			long parantId = en.getId();
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respListPsp.getResp().getResult())) {
				List<ListPspEntity> listResult = new ArrayList<ListPspEntity>();
				respListPsp.getPspList().getPsp().stream().forEach(result -> {
					ListPspEntity ob = new ListPspEntity();
					ob.setIdParant(parantId);
					ob.setCodes(result.getCodes());
					ob.setIsActive(result.getActive().toString());
					ob.setLastModifedTs(result.getLastModifedTs());
					ob.setName(result.getName());
					ob.setRecordDate(respDate);
					ob.setSpocEmail(result.getSpocEmail());
					ob.setSpocName(result.getSpocName());
					ob.setSpocPhone(result.getSpocPhone());
					ob.setUrl(result.getUrl());
					try {
						ob.setVersionSupported(JsonMan.getJSONStr(result.getVersionSupported()));
					} catch (Exception e) {
						e.printStackTrace();
						log.error("error while converting xml obj to json for VersionSupported ={}", e);
					}
					listResult.add(ob);
				});
				listPspRepo.saveAll(listResult);
			}

		} catch (Exception e) {
			log.error("errorin processing RespListPsp {}", e);
			ErrorLog.sendError(e, ReqRespVaePspKeysDaoImpl.class);
		}

	}

	@Override
	public void insertReq(ReqListVae reqListVae) {
		try {
			ReqRespVaePspKeysEntity en = new ReqRespVaePspKeysEntity();
			en.setReqDate(new Date());
			en.setReqHeadMsgId(reqListVae.getHead().getMsgId());
			en.setReqHeadOrgId(reqListVae.getHead().getOrgId());
			en.setReqHeadTs(reqListVae.getHead().getTs());
			en.setTxnId(reqListVae.getTxn().getId());
			en.setTxnTs(reqListVae.getTxn().getTs());
			en.setTxnType(reqListVae.getTxn().getType().toString());
			reqRespVaePspKeysRepo.save(en);
		} catch (Exception e) {
			log.error("error while insert reqListPsp {}", e);
			ErrorLog.sendError(e, ReqRespVaePspKeysDaoImpl.class);
		}
		
	}


	
}
