package com.npst.upiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SmsService {
	private static final Logger log = LoggerFactory.getLogger(SmsService.class);
	@Autowired
	Environment env;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private AmqpTemplate rabbitTemplate;

	public String sendMessage(String mobileNo, String message) {
		String userId = "SmartParking";
		String password = "3Ht@S-7k";
		String url = "http://www.smsjust.com/blank/sms/user/urlsms.php";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.ALL_VALUE);
		headers.set("Accept", MediaType.ALL_VALUE);
		RestTemplate restTemplate = new RestTemplate();
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("method", "sendMessage")
					.queryParam("username", userId).queryParam("pass", password).queryParam("senderid", "TMPYPK")
					.queryParam("message", message).queryParam("dest_mobileno", mobileNo).queryParam("response", "Y");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
					entity, String.class);
			log.trace("Send Message Response {}", response);
			String[] status = response.toString().split(" | ");
			if (status != null && status.length > 0) {
				String[] sendMessageStatus = status[1].split(",");
				// saveMessage(sendMessageStatus[1].toUpperCase(), message);//save to DB
			}
		} catch (Exception e) {
			log.error("Exception Caught SEND SMS {}", e);
		}
		return null;
	}

	public static void main(String[] args) {
		SmsService sms=new SmsService();
		sms.sendMessage("9540202589", "Do work in office not whatsapp.");
	}
	
/*	public void sendOtpToQueue(Message message) {
		try {
			String reqJsonMQ = mapper.writeValueAsString(message);
			rabbitTemplate.convertAndSend(Constant.PARKING_TO_OTP_QUEUE, reqJsonMQ);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendSmsToQueue(Message message) {
		try {
			String reqJsonMQ = mapper.writeValueAsString(message);
			rabbitTemplate.convertAndSend(Constant.PARKING_TO_SMS_QUEUE, reqJsonMQ);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

}