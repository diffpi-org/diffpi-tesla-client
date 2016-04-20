package cn.diffpi.security;

import java.util.HashMap;
import java.util.Map;

import cn.diffpi.kit.HttpCon;
import cn.diffpi.security.aes.AesTool;
import cn.diffpi.security.aes.Signature;

public class SecurityTest {
	public static void main(String[] args) throws Exception {
		AesTool aes = new AesTool();
		Signature signatureUtil = new Signature();

		String appid = "canairport001";
		String token = "aaaaa";//bbbb
		String key = aes.findKeyById(appid);
		long millis = System.currentTimeMillis();
		String xml = "{\"json\" : \"asdasd\"}";
		xml = aes.encrypt(xml, key);

		String lol = signatureUtil.digest(xml, "MD5");
		String signature = signatureUtil.generateSignature(appid, token, lol, millis);

		System.out.println(xml);//asdasdasaaa
		System.out.println(signature);

		
		Map<String, String> paraMap = new HashMap<String, String>();

		paraMap.put("sign", signature);
		paraMap.put("appid", appid);
		paraMap.put("timestamp", String.valueOf(millis));
		paraMap.put("lol", lol);

		HttpCon.doPost("http://localhost/yc/api/v1.0/track/entitys"+HttpCon.buildQuery(paraMap, "utf-8", "GET") , xml , "application/json" , null , false);
	}
}
