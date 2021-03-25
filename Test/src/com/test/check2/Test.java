package com.test.check2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Test {
	
	public void methname(String str2, int b) {
		
		// TODO Auto-generated method stub
				User user = null;
				List<String> name = new ArrayList<String>(); 
		        name.add("shujat"); 
		        name.add("abhishek");
		        List<String> city = new ArrayList<String>();
		        city.add("noida"); 
		        city.add("Delhi");
		        /*List<String> combine= new ArrayList<>();
		        combine.addAll(0, name);
		        combine.addAll(1,city);
		        System.out.println("Combine string>>>>"+combine);
		       
		        List<User> sBarang = new ArrayList<User>();
		       */
		       // sBarang.add((User) combine);
		        //System.out.println("Check"+sBarang);
		        
		        for (int i=0; i<name.size();i++) {
		        	for(int j=0;j<city.size();j++) {
		        	System.out.println(city.get(j));
		        	
		        	System.out.println(name.get(i));
		        	String str= city.get(j)+name.get(i);
		        	System.out.println(str);
		        	//user.setUser(str); 
		        	System.out.println(user);
		        	}
	}
		        }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = null;
		List<String> name = new ArrayList<String>(); 
        name.add("shujat"); 
        name.add("abhishek");
        List<String> city = new ArrayList<String>();
        city.add("noida"); 
        city.add("Delhi");
        /*List<String> combine= new ArrayList<>();
        combine.addAll(0, name);
        combine.addAll(1,city);
        System.out.println("Combine string>>>>"+combine);
       
        List<User> sBarang = new ArrayList<User>();
       */
       // sBarang.add((User) combine);
        //System.out.println("Check"+sBarang);
        
        for (int i=0; i<name.size();i++) {
        	for(int j=0;j<city.size();j++) {
        	System.out.println(city.get(j));
        	
        	System.out.println(name.get(i));
        	String str= city.get(j)+name.get(i);
        	System.out.println(str);
        	//user.setUser(str); 
        	System.out.println(user);
        	}
        }
       
	}
	

}
