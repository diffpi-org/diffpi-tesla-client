package cn.diffpi.rop.wechat.main;

import java.io.IOException;

import cn.diffpi.rop.wechat.info.config.WechatEnterpriseConfig;
import cn.diffpi.rop.wechat.service.WechatEnterprise;
import cn.diffpi.rop.wechat.service.WechatPublic;

import com.alibaba.fastjson.JSONObject;

public class Test {
	
	@org.junit.Test
	public void getMaterial(){
		WechatPublic wp = new WechatPublic("wxb4f222499f6fef6f", "92eb3d5e1b3982e627ea02ab72014a54");
		
		try {
			wp.getMaterial("eU8yzir2fkEgsjTzr4uiqW4RUYOZgYLsMSJ_KVL2myo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void getArticleTotal(){
		WechatPublic enterprise = new WechatPublic("wx5c48bff8b4b7ac13","9c4af599db5aca9d08ebbbad6bc4a16e");
				
		try {
			JSONObject result=enterprise.getArticleTotal("2015-12-18");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void getEnterpriseMaterialList(){
		WechatEnterprise enterprise = new WechatEnterprise("wx26a77beaed6922a8","HIC09DGg88KJRBnaXH8sfvOxXLC-dKxPTVDbF-V9SdReo_9i9dJFHPHIJC_JYX6b","12");
				
		try {
			String result=enterprise.getMaterialList(0).toString();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void getAgent(){
		WechatEnterprise enterprise = new WechatEnterprise(WechatEnterpriseConfig.CORPID, WechatEnterpriseConfig.SECRET, WechatEnterpriseConfig.AGENTID);
		
		System.out.println(enterprise.getAgent());
	}
	
}
