package com.npst.upiserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.service.PayAccTypeValidationService;

@Component
public class PayAccTypeValidationServiceImpl implements PayAccTypeValidationService {
	private static final Logger log = LoggerFactory.getLogger(PayAccTypeValidationServiceImpl.class);

	@Override
	public boolean isAccTypeValid(ReqResp req) {
		// TODO Please put validation of account type while doing DEBIT or CREDIT
		// TODO SB -> SB , SB -> CA, SB ->SOD , SB-> UOD (allowed) means Saving bank
		// account to any type bank account trf allowed
		// TODO CA -> SB, CA->SB, CA-> SOS , CA-> UOD (allowed) meand Current bank
		// account to any type bank account trf allowe
		// TODO UOD -> SB , UOD -> UOD , UOD -> SOD not allowd. only UOD -> CA allowed
		// if CA entity type is merchant.
		// TODO SOD -> SB , SOD -> SOD, SOD -> UOD , SOD -> CA allowed.
		// account
		// types:SAVINGS,CURRENT,DEFAULT,NRE,NRO,CREDIT,PPIWALLET,BANKWALLET,SOD,UOD
		log.info("inside start isAccTypeValid");
		boolean isValid = false;
		if ("DEBIT".equalsIgnoreCase(req.getTxnType()) || "ONUS".equalsIgnoreCase(req.getTxnType())
				||"mandateblock".equalsIgnoreCase(req.getTxnType())) {
			if ("PERSON".equalsIgnoreCase(req.getPayerType())) {
				isValid = validateDebitIfPayerPerson(req);
			} else if ("ENTITY".equalsIgnoreCase(req.getPayerType())) {
				isValid = validateDebitIfPayerEntity(req);
			} else {
				// not found required condition.
			}
		} else if ("CREDIT".equalsIgnoreCase(req.getTxnType())) {
			if ("PERSON".equalsIgnoreCase(req.getPayeeType())) {
				isValid = validateCreditIfPayeePerson(req);
			} else if ("ENTITY".equalsIgnoreCase(req.getPayeeType())) {
				isValid = validateCreditIfPayeeEntity(req);
			} else {
				// not found required condition.
			}
		} 
		else if("REVERSAL".equalsIgnoreCase(req.getTxnType())) {
			return true;
		}
		else {
			// not found required condition.
		}
		log.info("inside end isAccTypeValid ,isValid={}", isValid);
		return isValid;
	}

	private boolean validateDebitIfPayerPerson(ReqResp req) {
		// if DEBIT and Payer PERSON
		// TODO SB -> SB , SB -> CA, SB ->SOD , SB-> UOD (allowed) means Saving bank
		// account to any type bank account trf allowed
		// TODO CA -> SB, CA->SB, CA-> SOS , CA-> UOD (allowed) meand Current bank
		// account to any type bank account trf allowe
		// TODO UOD -> SB , UOD -> UOD , UOD -> SOD not allowd. only UOD -> CA allowed
		// if CA entity type is merchant.
		// TODO SOD -> SB , SOD -> SOD, SOD -> UOD , SOD -> CA allowed.
		boolean isValid = false;
		if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRO".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())|| "DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("CURRENT".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRO".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())|| "DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("DEFAULT".equalsIgnoreCase(req.getPayerAcType())) {
			isValid = true;
		} else if ("CREDIT".equalsIgnoreCase(req.getPayerAcType())) {

		} else if ("NRE".equalsIgnoreCase(req.getPayerAcType())) {
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRE".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())|| "DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}

		} else if ("NRO".equalsIgnoreCase(req.getPayerAcType())) {
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRE".equalsIgnoreCase(req.getPayeeAcType())|| "NRO".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())|| "DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}

		} else if ("SOD".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc

		} else if ("UOD".equalsIgnoreCase(req.getPayerAcType())) {
			if ("NRO".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("PPIWALLET".equalsIgnoreCase(req.getPayerAcType())) {

		} else if ("BANKWALLET".equalsIgnoreCase(req.getPayerAcType())) {

		} else {
			return isValid;
		}
		return isValid;
	}

	private boolean validateDebitIfPayerEntity(ReqResp req) {
		// if DEBIT and Payer ENTITY
		// TODO SB -> SB , SB -> CA, SB ->SOD , SB-> UOD (allowed) means Saving bank
		// account to any type bank account trf allowed
		// TODO CA -> SB, CA->SB, CA-> SOS , CA-> UOD (allowed) meand Current bank
		// account to any type bank account trf allowe
		// TODO UOD -> SB , UOD -> UOD , UOD -> SOD not allowd. only UOD -> CA allowed
		// if CA entity type is merchant.
		// TODO SOD -> SB , SOD -> SOD, SOD -> UOD , SOD -> CA allowed.
		boolean isValid = false;
		if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRO".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("CURRENT".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					|| "NRO".equalsIgnoreCase(req.getPayeeAcType())
					||"UOD".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("DEFAULT".equalsIgnoreCase(req.getPayerAcType())) {
			isValid = true;
			
		}
		
		else if ("DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
			isValid = true;
			
		}
		
		else if ("CREDIT".equalsIgnoreCase(req.getPayerAcType())) {

		} else if ("NRE".equalsIgnoreCase(req.getPayerAcType())) {
			if (!"NRO".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}

		} else if ("NRO".equalsIgnoreCase(req.getPayerAcType())) {
			
				isValid = true;
			

		} else if ("SOD".equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc

		} else if ("UOD".equalsIgnoreCase(req.getPayerAcType())) {
			if ("NRO".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if ("PPIWALLET".equalsIgnoreCase(req.getPayerAcType())) {

		} else if ("BANKWALLET".equalsIgnoreCase(req.getPayerAcType())) {

		} else {
			return isValid;
		}
		return isValid;
	}

	private boolean validateCreditIfPayeePerson(ReqResp req) {
		// if CREDIT and Payee PERSON
		// TODO SB -> SB , SB -> CA, SB ->SOD , SB-> UOD (allowed) means Saving bank
		// account to any type bank account trf allowed
		// TODO CA -> SB, CA->SB, CA-> SOS , CA-> UOD (allowed) meand Current bank
		// account to any type bank account trf allowe
		// TODO UOD -> SB , UOD -> UOD , UOD -> SOD not allowd. only UOD -> CA allowed
		// if CA entity type is merchant.
		// TODO SOD -> SB , SOD -> SOD, SOD -> UOD , SOD -> CA allowed.

		boolean isValid = false;
		if ("12".equals(req.getInitiationMode())) {
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType()) || "CURRENT".equalsIgnoreCase(req.getPayeeAcType())
					 || "NRO".equalsIgnoreCase(req.getPayeeAcType())|| "DEFAULT".equalsIgnoreCase(req.getPayeeAcType())
					) {
				isValid = true;//AS PER BANK NRE NOT ALLOW
			}
		} else {
			if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType())) {
				// check payer acc
				if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType()) || "CURRENT".equalsIgnoreCase(req.getPayerAcType())
						
						|| "NRO".equalsIgnoreCase(req.getPayerAcType()) || "UOD".equalsIgnoreCase(req.getPayerAcType())) {
					isValid = true;
				}
			} else if ("CURRENT".equalsIgnoreCase(req.getPayeeAcType())) {
				if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType()) || "CURRENT".equalsIgnoreCase(req.getPayerAcType())
						
						|| "NRO".equalsIgnoreCase(req.getPayerAcType())||"UOD".equalsIgnoreCase(req.getPayerAcType())) {
					isValid = true;
				}
			} else if ("DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			} else if ("CREDIT".equalsIgnoreCase(req.getPayeeAcType())) {

			} else if ("NRE".equalsIgnoreCase(req.getPayeeAcType())) {

				if (!"NRO".equalsIgnoreCase(req.getPayerAcType())) {
					isValid = true;
				}

			} else if ("NRO".equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;

			} else if ("SOD".equalsIgnoreCase(req.getPayeeAcType())) {
				// check payer acc

			} else if ("UOD".equalsIgnoreCase(req.getPayeeAcType())) {
				if ("NRO".equalsIgnoreCase(req.getPayerAcType())) {
					isValid = true;
				}
			} else if ("PPIWALLET".equalsIgnoreCase(req.getPayeeAcType())) {

			} else if ("BANKWALLET".equalsIgnoreCase(req.getPayeeAcType())) {
			} else {
				return isValid;
			}

		}
		return isValid;
	}

	private boolean validateCreditIfPayeeEntity(ReqResp req) {
		// if CREDIT and Payee PERSON
		// TODO SB -> SB , SB -> CA, SB ->SOD , SB-> UOD (allowed) means Saving bank
		// account to any type bank account trf allowed
		// TODO CA -> SB, CA->SB, CA-> SOS , CA-> UOD (allowed) meand Current bank
		// account to any type bank account trf allowe
		// TODO SOD -> SB , SOD -> SOD, SOD -> UOD , SOD -> CA allowed.
		// TODO UOD -> SB , UOD -> UOD , UOD -> SOD not allowd. only UOD -> CA allowed
		// if CA entity type is merchant.
		boolean isValid = false;
		if ("SAVINGS".equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType()) || "CURRENT".equalsIgnoreCase(req.getPayerAcType()) 
					||"NRO".equalsIgnoreCase(req.getPayerAcType())|| "UOD".equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if ("CURRENT".equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if ("SAVINGS".equalsIgnoreCase(req.getPayerAcType()) || "CURRENT".equalsIgnoreCase(req.getPayerAcType())
					 || "NRO".equalsIgnoreCase(req.getPayerAcType())||"UOD".equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if ("DEFAULT".equalsIgnoreCase(req.getPayeeAcType())) {
			isValid = true;
		} else if ("CREDIT".equalsIgnoreCase(req.getPayeeAcType())) {

		} else if ("NRE".equalsIgnoreCase(req.getPayeeAcType())) {
			if (!"NRO".equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}

		} else if ("NRO".equalsIgnoreCase(req.getPayeeAcType())) {
			isValid = true;

		} else if ("SOD".equalsIgnoreCase(req.getPayeeAcType())) {

		} else if ("UOD".equalsIgnoreCase(req.getPayeeAcType())) {
			if ("NRO".equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if ("PPIWALLET".equalsIgnoreCase(req.getPayeeAcType())) {
		} else if ("BANKWALLET".equalsIgnoreCase(req.getPayeeAcType())) {
		} else {

		}
		return isValid;
	}

}
