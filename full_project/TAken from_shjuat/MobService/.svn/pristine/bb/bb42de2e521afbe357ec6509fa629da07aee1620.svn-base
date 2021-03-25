package com.npst.mobileservice.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.FeedBackHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.RespJson;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.ErrorCode;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Feedback;
import com.npst.upi.hor.MasterConfig;
import com.npst.upi.hor.Registration;

public class FeedBackService {
	private static final Logger log = Logger.getLogger(FeedBackService.class);
	private static String feedBackAttach = null;
	String osName = System.getProperty("os.name");
	FeedBackHome registrationHome = null;
	private static int feedbackCount = 1;
	private static final String feedbackLimit = Util.getProperty("feedbackLimit");
	private static final String imagesLimit = Util.getProperty("imagesLimit");

	public RespJson saveFeedback(String strBuild) {
		log.info(strBuild);
		ReqJson reqJson = JSONConvert.getReqJson(strBuild);
		RespJson respJson = new RespJson();
		String imagesName = "";
		try {
			if (null == registrationHome) {
				registrationHome = new FeedBackHome();
			}
			List<Feedback> feedbacks = registrationHome.findByRegIdAndSDate(Integer.parseInt(reqJson.getRegId()),
					Util.getOnlyDate());
			log.info("Feedback list got : " + feedbacks);
			if (null != feedbacks && feedbacks.size() < 5) {
				Registration reg = registrationHome.findByRegId(Integer.parseInt(reqJson.getRegId()));
				if (null != reqJson.getImage()
						&& reqJson.getImage().trim().length() > Integer.parseInt(feedbackLimit)) {
					String[] value_split = reqJson.getImage().split("\\|");
					if (value_split.length > Integer.parseInt(imagesLimit)) {
						respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FEEDBACK_IMAGES_EXCED.getCode());
						respJson.setMsgId(ConstantI.MSGID_FAIL);
						return respJson;
					}
					for (String encImage : value_split) {
						long imageName = System.currentTimeMillis();
						saveDocument(reg, encImage, imageName);
						imagesName = imageName + "~";
					}
					imagesName = imagesName.substring(0, imagesName.length() - 1);
				}
				saveFeedback(reqJson.getFeedbackCategory(), reqJson.getFeedbackSeverity(), reqJson.getRemarks(),
						reqJson.getEmail(), reqJson.getFeedbackText(), reqJson.getScreenPath(), reqJson.getRegId(),
						reg.getMobno(), reqJson.getAppVersion(), reqJson.getHandsetName(), reqJson.getOsName(),
						reqJson.getOsVersion(), imagesName);
				respJson.setMsgId(ConstantI.MSGID_SUCCESS);
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_SUCCESS_FEEDBACK.getCode());
			} else {
				respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_FEEDBACK_BLOCKED_MSG.getCode());
				respJson.setMsgId(ConstantI.MSGID_FAIL);
			}

		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info("return in:" + s);
			respJson.setMsgId(ConstantI.MSGID_FAIL);
			respJson.setMsg(ErrorCode.AcqErrorCode.MSGID_EXCEPTIONMSG.getCode());
		}
		return respJson;
	}

	private void saveFeedback(String feedbackCategory, String feedbackSeverity, String remarks, String email,
			String feedbackText, String screenPath, String regId, String mobNumber, String appVersion,
			String handsetName, String osName, String osVersion, String imagesName) throws ParseException {
		Feedback feedback = new Feedback();
		screenPath.replaceAll("Fragment", "");
		List<MasterConfig> configs = registrationHome.findByName(screenPath);
		String pathCode = configs.get(0).getCode();
		feedback.setAttachmentPath(feedBackAttach);
		feedback.setEmail(email);
		feedback.setFeedback_date(Util.getOnlyDate());
		feedback.setFeedbackCategory(Integer.parseInt(feedbackCategory));
		feedback.setFeedbackSeverity(Integer.parseInt(feedbackSeverity));
		feedback.setFeedbackText(feedbackText);
		feedback.setRegId(Integer.parseInt(regId));
		feedback.setRemarks(remarks);
		feedback.setScreenPath(pathCode);
		feedback.setStatus(1);
		feedback.setApp_version(appVersion);
		feedback.setHandset_name(handsetName);
		feedback.setOs_version(osVersion);
		feedback.setOs_name(osName);
		feedback.setMobile_no(mobNumber);
		feedback.setImagesName(imagesName);
		registrationHome.saveFeedback(feedback);
	}

	private void saveDocument(Registration reg, String encImage, long imageName) throws IOException {
		String mobileNumber = reg.getMobno();
		String date = Util.getStringDate();
		if (osName.toLowerCase().contains("windows"))
			feedBackAttach = "c:\\conf\\" + date + "\\" + mobileNumber;
		else
			feedBackAttach = "/home/npst/" + date + "/" + mobileNumber;

		String filePath = feedBackAttach + "/" + imageName;
		File f = new File(filePath);
		feedbackCount++;
		if (!f.exists())
			f.mkdirs();
		BufferedImage image = null;
		byte[] imageByte;
		imageByte = Util.base64Decode(encImage);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		ImageIO.write(image, "jpg", f);
		bis.close();
	}
}
