package com.npst.middleware.iso;
import org.jpos.iso.IFA_AMOUNT;
import org.jpos.iso.IFA_BINARY;
import org.jpos.iso.IFA_BITMAP;
import org.jpos.iso.IFA_LLBINARY;
import org.jpos.iso.IFA_LLCHAR;
import org.jpos.iso.IFA_LLLBINARY;
import org.jpos.iso.IFA_LLLCHAR;
import org.jpos.iso.IFA_LLNUM;
import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;
public class ATMPackager extends ISOBasePackager
{
	  protected ISOFieldPackager[] fld = {
/*0*/	    new IFA_NUMERIC(4, "Message Type Indicator"), 
/*1*/	    new IFA_BITMAP(16, "Bitmap"), 
/*2*/	    new IFA_LLNUM(19, "Primary Account number"), 
/*3*/	    new IFA_NUMERIC(6, "Processing Code"), 
/*4*/	    new IFA_NUMERIC(12, "Amount, Transaction"), 
/*5*/	    new IFA_NUMERIC(12, "Amount, Reconciliation"), 
/*6*/	    new IFA_NUMERIC(12, "Amount, Cardholder billing"), 
/*7*/	    new IFA_NUMERIC(10, "Date and time, transmission"), 
/*8*/	    new IFA_NUMERIC(8, "Amount, Cardholder billing fee"), 
/*9*/	    new IFA_NUMERIC(8, "Conversion rate, Reconciliation"), 
/*10*/	new IFA_NUMERIC(8, "Conversion rate, Cardholder billing"), 
/*11*/	new IFA_NUMERIC(6, "Systems trace audit number"), 
/*12*/	new IFA_NUMERIC(6, "Date and time, Local transaction"), 
/*13*/	new IFA_NUMERIC(4, "Date, Effective"), 
/*14*/	new IFA_NUMERIC(4, "Date, Expiration"), 
/*15*/	new IFA_NUMERIC(6, "Date, Settlement"), 
/*16*/	new IFA_NUMERIC(4, "Date, Conversion"), 
/*17*/	new IFA_NUMERIC(4, "Date, Capture"), 
/*18*/	new IFA_NUMERIC(4, "Merchant type"), 
/*19*/	new IFA_NUMERIC(3, "Country code, Acquiring institution"), 
/*20*/	new IFA_NUMERIC(3, "Country code, Primary account number"), 
/*21*/	new IFA_NUMERIC(3, "Country code, Forwarding institution"), 
/*22*/	new IF_CHAR(12, "Point of service data code"), 
/*23*/	new IFA_NUMERIC(3, "Card sequence number"), 
/*24*/	new IFA_NUMERIC(3, "Function code"), 
/*25*/	new IFA_NUMERIC(4, "Message reason code"), 
/*26*/	new IFA_NUMERIC(4, "Card acceptor business code"), 
/*27*/	new IFA_NUMERIC(1, "Approval code length"), 
/*28*/	new IFA_NUMERIC(6, "Date, Reconciliation"), 
/*29*/	new IFA_NUMERIC(3, "Reconciliation indicator"), 
/*30*/	new IFA_NUMERIC(24, "Amounts, original"), 
/*31*/	new IFA_LLCHAR(99, "Acquirer reference data"), 
/*32*/	new IFA_LLNUM(11, "Acquirer institution identification code"), 
/*33*/	new IFA_LLNUM(11, "Forwarding institution identification code"), 
/*34*/	new IFA_LLCHAR(28, "Primary account number, extended"), 
/*35*/	new IFA_LLCHAR(37, "Track 2 data"), 
/*36*/	new IFA_LLLCHAR(104, "Track 3 data"), 
/*37*/	new IF_CHAR(12, "Retrieval reference number"), 
/*38*/	new IF_CHAR(6, "Approval code"), 
/*39*/	new IFA_NUMERIC(2, "Action code"), 
/*40*/	new IFA_NUMERIC(3, "Service code"), 
/*41*/	new IF_CHAR(8, "Card acceptor terminal identification"), 
/*42*/	new IF_CHAR(15, "Card acceptor identification code"), 
/*43*/	new IFA_LLCHAR(99, "Card acceptor name/location"), 
/*44*/	new IFA_LLCHAR(99, "Additional response data"), 
/*45*/	new IFA_LLCHAR(76, "Track 1 data"), 
/*46*/	new IFA_LLLCHAR(204, "Amounts, Fees"), 
/*47*/	new IFA_LLLCHAR(999, "Additional data - national"), 
/*48*/	new IFA_LLLCHAR(999, "Additional data - private"), 
/*49*/	new IF_CHAR(3, "Currency code, Transaction"), 
/*50*/	new IF_CHAR(3, "Currency code, Reconciliation"), 
/*51*/	new IF_CHAR(3, "Currency code, Cardholder billing"), 
/*52*/	new IFA_BINARY(8, "Personal identification number (PIN) data"), 
/*53*/	new IFA_LLBINARY(48, "Security related control information"), 
/*54*/	new IFA_LLLCHAR(120, "Amounts, additional"), 
/*55*/	new IFA_LLLBINARY(255, "IC card system related data"), 
/*56*/	new IFA_LLNUM(35, "Original data elements"), 
/*57*/	new IFA_NUMERIC(3, "Authorization life cycle code"), 
/*58*/	new IFA_LLNUM(11, "Authorizing agent institution Id Code"), 
/*59*/	new IFA_LLLCHAR(999, "Transport data"), 
/*60*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*61*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*62*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*63*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*64*/	new IFA_BINARY(8, "Message authentication code field"), 
/*65*/	new IFA_BINARY(8, "Reserved for ISO use"), 
/*66*/	new IFA_LLLCHAR(204, "Amounts, original fees"), 
/*67*/	new IFA_NUMERIC(2, "Extended payment data"), 
/*68*/	new IFA_NUMERIC(3, "Country code, receiving institution"), 
/*69*/	new IFA_NUMERIC(3, "Country code, settlement institution"), 
/*70*/	new IFA_NUMERIC(3, "Country code, authorizing agent Inst."), 
/*71*/	new IFA_NUMERIC(8, "Message number"), 
/*72*/	new IFA_LLLCHAR(999, "Data record"), 
/*73*/	new IFA_NUMERIC(6, "Date, action"), 
/*74*/	new IFA_NUMERIC(10, "Credits, number"), 
/*75*/	new IFA_NUMERIC(10, "Credits, reversal number"), 
/*76*/	new IFA_NUMERIC(10, "Debits, number"), 
/*77*/	new IFA_NUMERIC(10, "Debits, reversal number"), 
/*78*/	new IFA_NUMERIC(10, "Transfer, number"), 
/*79*/	new IFA_NUMERIC(10, "Transfer, reversal number"), 
/*80*/	new IFA_NUMERIC(10, "Inquiries, number"), 
/*81*/	new IFA_NUMERIC(10, "Authorizations, number"), 
/*82*/	new IFA_NUMERIC(10, "Inquiries, reversal number"), 
/*83*/	new IFA_NUMERIC(10, "Payments, number"), 
/*84*/	new IFA_NUMERIC(10, "Payments, reversal number"), 
/*85*/	new IFA_NUMERIC(10, "Fee collections, number"), 
/*86*/	new IFA_NUMERIC(16, "Credits, amount"), 
/*87*/	new IFA_NUMERIC(16, "Credits, reversal amount"), 
/*88*/	new IFA_NUMERIC(16, "Debits, amount"), 
/*89*/	new IFA_NUMERIC(16, "Debits, reversal amount"), 
/*90*/	new IFA_NUMERIC(10, "Authorizations, reversal number"), 
/*91*/	new IFA_NUMERIC(3, "Country code, transaction Dest. Inst."), 
/*92*/	new IFA_NUMERIC(3, "Country code, transaction Orig. Inst."), 
/*93*/	new IFA_LLNUM(11, "Transaction Dest. Inst. Id code"), 
/*94*/	new IFA_LLNUM(11, "Transaction Orig. Inst. Id code"), 
/*95*/	new IFA_LLCHAR(99, "Card issuer reference data"), 
/*96*/	new IFA_LLLBINARY(999, "Key management data"), 
/*97*/	new IFA_AMOUNT(17, "Amount, Net reconciliation"), 
/*98*/	new IF_CHAR(25, "Payee"), 
/*99*/	new IFA_LLCHAR(11, "Settlement institution Id code"), 
/*100*/	new IFA_LLNUM(11, "Receiving institution Id code"), 
/*101*/	new IFA_LLCHAR(17, "File name"), 
/*102*/	new IFA_LLCHAR(28, "Account identification 1"), 
/*103*/	new IFA_LLCHAR(28, "Account identification 2"), 
/*104*/	new IFA_LLLCHAR(100, "Transaction description"), 
/*105*/	new IFA_NUMERIC(16, "Credits, Chargeback amount"), 
/*106*/	new IFA_NUMERIC(16, "Debits, Chargeback amount"), 
/*107*/	new IFA_NUMERIC(10, "Credits, Chargeback number"), 
/*108*/	new IFA_NUMERIC(10, "Debits, Chargeback number"), 
/*109*/	new IFA_LLCHAR(84, "Credits, Fee amounts"), 
/*110*/	new IFA_LLCHAR(84, "Debits, Fee amounts"), 
/*111*/	new IFA_LLLCHAR(999, "Reserved for ISO use"), 
/*112*/	new IFA_LLLCHAR(999, "Reserved for ISO use"), 
/*113*/	new IFA_LLLCHAR(999, "Reserved for ISO use"), 
/*114*/	new IFA_LLLCHAR(999, "Reserved for ISO use"), 
/*115*/	new IFA_LLLCHAR(999, "Reserved for ISO use"), 
/*116*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*117*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*118*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*119*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*120*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*121*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*122*/	new IFA_LLLCHAR(999, "Reserved for national use"), 
/*123*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*124*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*125*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*126*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*127*/	new IFA_LLLCHAR(999, "Reserved for private use"), 
/*128*/	new IFA_BINARY(8, "Message authentication code field") };
	  	  public ATMPackager()
	  {
	    setFieldPackager(this.fld);
	  }
	}
