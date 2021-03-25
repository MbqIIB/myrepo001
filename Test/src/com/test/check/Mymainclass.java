package com.test.check;
 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;;;
public class Mymainclass {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test t= new Test();
		//t.methname("bhaskar",1);
	 	
		//Date dt = new Date();
		//dt.getDate();
		
			
			/*LocalDate bday = LocalDate.of(1999, Month.MAY, 19); 
			LocalDate today = LocalDate.now(); 
			Period age = Period.between(bday, today);
			int years = age.getYears();
			int months = age.getMonths();
			System.out.println("number of years: " + years);
			System.out.println("number of months: " + months);*/
			//getting diffrence 
		    /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		    try {
		    Date firstDate = sdf.parse("06/12/2017");
		    Date secondDate = sdf.parse("06/03/2018");
		    
		    Calendar c = Calendar.getInstance(); 
		    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    c.setTimeInMillis(diffInMillies);
		   
		    System.out.println("<><><><");
		    }catch(Exception ex) 
		    {
		    	ex.printStackTrace();
		    }*/
		    
		String date1 = "15-JAN-2015";
        String date2 = "12-APR-2015";

        DateFormat formater = new SimpleDateFormat("dd-MMM-yyyy");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formater.parse(date1));
            finishCalendar.setTime(formater.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (beginCalendar.before(finishCalendar)) {
            // add one month to date per loop
            String date = formater.format(beginCalendar.getTime()).toUpperCase();
            System.out.println(date);
            beginCalendar.add(Calendar.MONTH, 1);
           // if () 
            {
            	
            }
        }
       
	}

}
