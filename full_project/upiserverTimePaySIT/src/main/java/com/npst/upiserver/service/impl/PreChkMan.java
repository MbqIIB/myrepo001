package com.npst.upiserver.service.impl;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.npcischema.ReqMandate;

public class PreChkMan {

	public static boolean precheck(ReqMandate reqMandate) {
		
		//try
		
		 boolean flag=false;
		
		if(ConstantI.M_CREATE.equalsIgnoreCase(reqMandate.getTxn().getType().toString()) || ConstantI.M_UPDATE.equalsIgnoreCase(reqMandate.getTxn().getType().toString())||
				ConstantI.M_REVOKE.equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
			if(reqMandate.getMandate().getBlockFund()!=null && reqMandate.getMandate().getUmn()!=null && reqMandate.getMandate().getRecurrence().getPattern()!=null){
				
				if(ConstantI.ACCOUNT.equalsIgnoreCase(reqMandate.getPayer().getAc().getAddrType().toString())) {
					
					
					return flag=true;
					
				}	
				
				return flag;
			}	
		
		}
		
		return flag;
	}

}
