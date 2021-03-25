/*package com.npst.mobileservice.util;

public class SendSmsPinnacle {

	static class sendSmsWorker implements Runnable {
       String mobileno=null;
       String message=null;

       public sendSmsWorker(String mobileno, String message) {
			this.mobileno = mobileno;
			this.message = message;
		}

		@Override
		public void run() {
          
		}
		
		
	}


	
	public static void sendSmsPinnacle(final String mobile, final String message) {
		ThreadPool.getThreadFrmTP().execute(new sendSmsWorker(mobile, message));
	}
}
*/