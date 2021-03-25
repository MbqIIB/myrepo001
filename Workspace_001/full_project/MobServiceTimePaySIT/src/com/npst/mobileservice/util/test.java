package com.npst.mobileservice.util;

public class test {
	public static void main(String[] arg) {

		String s = "21,22,1,2";
		System.out.println(calQus(s));

		// String ss =
		// "{\"credJsons\":[{\"data\":{\"code\":\"NPCI\",\"encryptedBase64String\":\"2.0|d/PdZpVx4slntR3fX1YzXM3XjeWSALvuLrN1UKYZiCVMN4H40/Hk6PtujywvNfYwDS8HSgEMSbflFOYZMU5Gs+w1gDhg6dgJAm+DVFtL2b6C6zRj4jRx7WpKYHPhWkONGNjF/XByN0WcBzrcWzKoPTY1gspBkupfURTIoqazadhKDKUqWQ/ySdFxFizIRGht3ggSD7YRJSLE9qqNBhTglQFXd2P+zpkCmF00zbPkh/1dB2eXiB3cqj3xL6WqILXEHd4OiIi9D6R87HuT9qppL46MCZM8b4qan3qZS2ubBmvc2ft1SEvzPcJip58XOMyXW0L4ICo8tDDAVB31+oN4Vw==\",\"hmac\":\"null\",\"ki\":\"20150822\",\"skey\":\"\",\"type\":\"null\"},\"subType\":\"NMPIN\",\"type\":\"PIN\"},{\"data\":{\"code\":\"NPCI\",\"encryptedBase64String\":\"2.0|DL2S8tSqTPLOEyQBxHd5BMXVI4lil/lVlslCxK00eAs+9q2tGukR53tGxlDrgVqb/BpYhjuAGRfyNlxSWPohcbHafb8xn404h9wxZ0hRBwal/jIYEeHWis89i867s6eg8rhyUWM6eplfqjlXJtDEgq8ALFLU2ncnWwjj+ln0eWZADXaGlFyFn+K/h9fG8SXimYrCBSrKQh/5e9fTmMy+6WhCbwMRDx5nbprIhfffMexzTmTl2DWnXJZkPVCKZjHhYBNOZMe8CN+x1ugCCocNNYK17hgAnr6BpfzyrS7LWYCotWmWGSEMPI8gyxLtwL32HYJ2n2UeYUY7pKjX4WgSsQ==\",\"hmac\":\"null\",\"ki\":\"20150822\",\"skey\":\"\",\"type\":\"null\"},\"subType\":\"MPIN\",\"type\":\"PIN\"}],\"mobileNo\":\"919718127888\",\"payerAcNum\":\"0900501016560\",\"payerAcType\":\"SAVINGS\",\"payerAddr\":\"9718127888@cosmos\",\"payerAddrType\":\"ACCOUNT\",\"payerCode\":\"0000\",\"payerDeviceAppId\":\"com.npst.upi.timepay\",\"payerDeviceCapability\":\"520000020001000411016919718127888
		// \",\"payerDeviceGeoCode\":\"18.5376348,73.8346411\",\"payerDeviceId\":\"980c304813b1e8a0\",\"payerDeviceIp\":\"100.68.55.226\",\"payerDeviceLocation\":\"Pune\",\"payerDeviceMobile\":\"919718127888\",\"payerDeviceOsType\":\"ANDROID
		// 7.0\",\"payerDeviceType\":\"MOB\",\"payerIfsc\":\"COSB0000000\",\"payerName\":\"KEDARI
		// VINAYAK
		// FAKIRAPPA\",\"payerSeqNum\":\"1\",\"payerType\":\"PERSON\",\"regDetailsType\":\"FORMAT2\",\"regId\":\"5\",\"regMobile\":\"919718127888\",\"txnFlag\":\"SETCRE\",\"txnId\":\"COB37CF6BF7862C48739D3D454F07FFED27\",\"txnNote\":\"Setting
		// Credentials\",\"txnRefId\":\"COB37CF6BF7862C48739D3D454F07FFED27\",\"txnRefUrl\":\"https://www.cosmosbank.com/\",\"txnType\":\"SetCre\"}";
		//
		// ReqJson reqJson=JSONConvert.getReqJson(ss);
		//
		//
		// if(Validations.jsonValidation(reqJson)) {
		// System.out.println("true");
		// }else {
		// System.out.println("false");
		// }

		// System.out.println(ss.replaceAll("[^a-zA-Z ]", ""));
	}

	public static String calQus(String s) {
		String finalStr = "";
		String[] a = s.split(",");
		for (int i = 0; i < a.length; i++) {
			finalStr += "?,";

		}
		// System.out.println(finalStr.substring(0, finalStr.length() - 1));
		return finalStr.substring(0, finalStr.length() - 1);

	}
}
