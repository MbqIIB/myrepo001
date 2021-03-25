package com.npst.mobileservice.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.ListPSPKeyDao;
import com.npst.mobileservice.dao.ListVaeDao;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.util.ECSignQRUtil;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.ListPspKeysEntity;
import com.npst.upi.hor.ListVaeEntity;




public class SignedQrVerificationService {

	private static final Logger log = Logger.getLogger(SignedQrVerificationService.class);
	public static final String signKey="SHA256withECDSA";
	private static ListVaeDao vaeDao = null;
	private static ListPSPKeyDao listPspKeydao = null;

	public boolean findByAddr(ReqJson reqJson) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		log.info("Request String is "+reqJson.toString());
	    boolean isVerified = true;
	    PublicKey publicKey = null;
	    PrivateKey privateKey = null;
	    //PublicKey publicKey = null;
	    Object obj = null;
		if (null ==vaeDao) {
			vaeDao = new ListVaeDao();
		}
		if (null == listPspKeydao) {
			listPspKeydao = new ListPSPKeyDao();
		}
		if (reqJson.getOrgId().equalsIgnoreCase(Util.getProperty("DEFAULT_ORG"))) {
			log.info("For Merchant Verification"+reqJson.getOrgId());
			ListVaeEntity isMerchant = vaeDao.getDetail(reqJson.getOrgId());
			if (null != isMerchant) {
				log.info("If not null "+reqJson.getOrgId());
				String pubkey = isMerchant.getKeyValue();
				//isVerified =signVerification(reqJson.getContent(),reqJson.getSign(), pubkey);
				obj =pubkey;
			     if (obj instanceof PublicKey) {
			    	 publicKey = (PublicKey)obj;
				}
				
				try {
					isVerified =ECSignQRUtil.verify(reqJson.getContent(),reqJson.getSign(),publicKey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			log.info("For PSP Verification");
			ListPspKeysEntity listPSPDetails = listPspKeydao.getDetail(reqJson.getOrgId());
			log.info("For PSP Verification="+reqJson.getOrgId());
			if (null != listPSPDetails) {
				log.info("If not null PSP"+reqJson.getOrgId());
				//isVerified =signVerification(reqJson.getContent(),reqJson.getSign(), listPSPDetails.getKeyValue());
				String pubkey = listPSPDetails.getKeyValue();
				//isVerified =signVerification(reqJson.getContent(),reqJson.getSign(), pubkey);
				log.info("publicKey database is="+pubkey);
				 
				try {
					publicKey=ECSignQRUtil.loadPublicKey(null, "EC", pubkey);
					log.info("publicKey is="+publicKey);
					log.info("publicKey is inside try block="+publicKey);
				Map<String,String> map=	getOrgQrConent(reqJson.getContent());
				log.info("get Data from map"+map.get("qr"));
					isVerified = ECSignQRUtil.verify(map.get("qr"), reqJson.getSign(), publicKey);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		log.info("isVerified is as {} "+isVerified);
		return  true;
	}
	
public Map<String,String>  getOrgQrConent(String s) throws Exception{
		
		try {
			Map<String,String> map=new HashMap<>();
			String qrplain="";
			String sign="";
			String orgid="";
			if (s.startsWith("upi:")) {
				if (s.indexOf("&sign") < s.indexOf("&orgId")) {
					qrplain=s.substring(0,s.indexOf("&sign"));
					sign=s.substring(s.indexOf("&sign"),s.indexOf("&orgId"));
					orgid=s.substring(s.indexOf("&orgId"));
				} else {
					qrplain=s.substring(0,s.indexOf("&orgId"));
					sign=s.substring(s.indexOf("&orgId"),s.indexOf("&sign"));
					orgid=s.substring(s.indexOf("&sign"));
				}
			}else {
				//we have another logic
			}
			sign=sign.replaceFirst("&sign=", "");
			orgid=orgid.replaceFirst("&orgid=", "");
			map.put("qr", qrplain);
			map.put("sign", sign);
			map.put("orgid", orgid);
			System.out.println("QRPLAIN="+qrplain);
			System.out.println("SIGN="+sign);
			System.out.println("ORGID="+orgid);
			return map;
		}catch (Exception e) {
		  e.printStackTrace();
		  throw e;
		}
	}

	/*private PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		publicKey = publicKey.replaceAll("\\n", "");
		publicKey = publicKey.replace("-----BEGIN RSA PUBLIC KEY-----", "");
		publicKey = publicKey.replace("-----END RSA PUBLIC KEY-----", "");
		publicKey = publicKey.trim();
		byte[] keyDecoded = Base64.getDecoder().decode(publicKey.getBytes());
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keyDecoded);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pubKey = kf.generatePublic(publicSpec);
		return pubKey;
	}*/

	public static boolean signVerification(String content,String sign,String pubkey) throws UnsupportedEncodingException {
		boolean isVerifiedSign=true;
		/*try {
			Signature dsa = Signature.getInstance(signKey);
			dsa.initVerify(pubkey);
			dsa.update(content.getBytes("UTF-8"));
			byte[] decodedSign = java.util.Base64.getDecoder().decode(sign);
			isVerifiedSign = dsa.verify(decodedSign);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException  e) {
			e.printStackTrace();
		}*/
		return isVerifiedSign;
	}

}
