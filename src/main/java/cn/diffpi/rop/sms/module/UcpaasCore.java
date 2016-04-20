package cn.diffpi.rop.sms.module;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.HttpCon;
import cn.diffpi.rop.sms.module.request.Sms;
import cn.diffpi.rop.sms.module.request.TemplateSMS;
import cn.diffpi.rop.sms.util.EncryptUtil;
import cn.diffpi.rop.sms.util.UcpaasInfo;
import cn.dreampie.common.util.json.Jsoner;

import com.alibaba.fastjson.JSONObject;

public class UcpaasCore {
	/***
	 * 发送注册验证码
	 * @param phones
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendSms(String[] phones, String code) throws Exception {
		Sms sms = new Sms();
		TemplateSMS templateSMS = new TemplateSMS();

		templateSMS.setAppId(UcpaasInfo.APP_ID);
		templateSMS.setTemplateId(UcpaasInfo.TEMPLATE_ID);

		templateSMS.setTo(arrayToString(phones));
		templateSMS.setParam(code);
		sms.setTemplateSMS(templateSMS);

		return sendSms(sms);
	}
	
	public static JSONObject sendCusSms(String[] phones, String content) throws Exception {
		Sms sms = new Sms();
		TemplateSMS templateSMS = new TemplateSMS();

		templateSMS.setAppId(UcpaasInfo.APP_ID);
		templateSMS.setTemplateId(UcpaasInfo.CUS_TEMPLATE_ID);

		templateSMS.setTo(arrayToString(phones));
		templateSMS.setParam(content);
		sms.setTemplateSMS(templateSMS);

		return sendSms(sms);
	}
	
	/***
	 * 发送找回密码的验证码
	 * @param phones
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendUpPwdSms(String phones , String code) throws Exception {
		Sms sms = new Sms();
		TemplateSMS templateSMS = new TemplateSMS();

		templateSMS.setAppId(UcpaasInfo.APP_ID);
		templateSMS.setTemplateId(UcpaasInfo.UP_PWD_ID);

		templateSMS.setTo(phones);
		templateSMS.setParam(code+","+1);
		sms.setTemplateSMS(templateSMS);

		return sendSms(sms);
	}

	/***
	 * 发送找回密码的验证码
	 * @param phones
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static boolean sendUpPwdSmsBaclBool(String phones, String code) throws Exception {
		JSONObject jsonObj = sendUpPwdSms(phones, code);
		if (jsonObj.getJSONObject("resp").getString("respCode").equals("000000")) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 发送注册验证码
	 * @param phones
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static boolean sendSmsBackBool(String[] phones, String code) throws Exception {
		JSONObject jsonObj = sendSms(phones, code);
		if (jsonObj.getJSONObject("resp").getString("respCode").equals("000000")) {
			return true;
		} else {
			return false;
		}
	}

	private static String arrayToString(String[] strArr) {
		try {
			String strs = "";
			for (String str : strArr) {
				strs += str + ",";
			}
			return strs.substring(0, strs.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/***
	 * 得到随机码
	 * 
	 * @param length
	 * @return
	 */
	public static String randomCode(int length) {
		Random random = new Random();

		String ranStr = "";
		for (int i = 0; i < length; i++) {
			int ran = random.nextInt(8) + 1;
			ranStr += ran;
		}

		return ranStr;
	}

	private static JSONObject sendSms(Sms sms) throws Exception {
		String smsJsonStr = Jsoner.toJSON(sms);
		
		String timestamp = DateUtil.getCurrentDate(DateUtil.MIN_TIME);

		String outStr = HttpCon.doPost(getUrl(timestamp), smsJsonStr, null, getHeaderMap(timestamp), true);
		
		JSONObject jsonObj = Jsoner.toObject(outStr);
		
		return jsonObj;
	}

	private static String getSignature(String accountId, String authToken, String timestamp) {
		EncryptUtil encryptUtil = new EncryptUtil();

		String sig = accountId + authToken + timestamp;
		try {
			return encryptUtil.md5Digest(sig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getUrl(String timestamp) {
		String url = UcpaasInfo.HOST_REST_URL + UcpaasInfo.JSON_URL;

		String sig = getSignature(UcpaasInfo.ACCOUNT_SID, UcpaasInfo.AUTH_TOKEN, timestamp);

		try {
			return MessageFormat.format(url, UcpaasInfo.ACCOUNT_SID, sig);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getAuthorization(String timestamp) {
		String src = UcpaasInfo.ACCOUNT_SID + ":" + timestamp;

		EncryptUtil encryptUtil = new EncryptUtil();
		try {
			return encryptUtil.base64Encoder(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, String> getHeaderMap(String timestamp) {
		String ctype = "application/json;charset=utf-8";
		String accept = "application/json";
		String auth = getAuthorization(timestamp);

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", ctype);
		headerMap.put("Accept", accept);
		headerMap.put("Authorization", auth);

		return headerMap;
	}
}
