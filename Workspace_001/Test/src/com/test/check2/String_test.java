package com.test.check2;
import java.util.ArrayList;

public class String_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str2="";
		User user = new User();
		String result = null;
		String city ="rrn=102125636679, txnType=DEBIT, txnId=YBLd496b812b106493c9fcb1e7784b34813, txnRefId=BCR2DN6T4XK";
		String[] cityArr = city.split("\\+");
		
		ArrayList<String> list2 = new ArrayList<String>();	 
		for (String str : cityArr)       	 
        		 {				 
					System.out.println(str);
					System.out.println("DATA>>>>>" + str2);
				
					list2.add(str2);
			        System.out.println(list2);
        		 }
         
	     user.setUser(list2);
	     //System.out.println(user);
		 System.out.println("<><><><>"+user);
		
	}

}
