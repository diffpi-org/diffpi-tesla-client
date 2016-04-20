package cn.diffpi.rop.wechat.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.diffpi.kit.HttpCon;
import cn.diffpi.rop.wechat.info.config.WechatPublicConfig;
import cn.diffpi.rop.wechat.token.AccessToken;
import cn.diffpi.rop.wechat.token.TokenManager;
import cn.dreampie.common.Constant;
import cn.dreampie.common.util.json.Jsoner;

import com.alibaba.fastjson.JSONObject;

public class WechatPublic {
	
	private String appid = null;
	private String secret = null;
	
	public WechatPublic(String appid , String secret){
		this.appid = appid;
		this.secret = secret;
	}
	
	private  AccessToken getAccessToken(){
		try {
			String url = WechatPublicConfig.ACCESS_TOKEN_URL.replace("APPID", "{0}").replace("APPSECRET", "{1}");
			
			return TokenManager.getAccessToken(url , appid, appid , secret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new AccessToken();
	}
	
	/***
	 * 获取素材列表
	 * @return
	 */
	public JSONObject getMaterialList(int offset){
		String url = WechatPublicConfig.GET_MATERIALLIST_URL.replace("ACCESS_TOKEN", getAccessToken().getToken());
		Map<String, Object> requestMap=new HashMap<String, Object>();
		requestMap.put("type", "news");
		requestMap.put("offset", offset);
		requestMap.put("count", "20");
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doPost(url, Jsoner.toJSON(requestMap).toString(), null, null, true));
		return jsonObj;
	}
	
	/***
	 * 获取素材
	 * @return
	 */
	public String getMaterial(String media_id) throws IOException{
		String url = WechatPublicConfig.GET_MATERIAL_URL.replace("ACCESS_TOKEN", getAccessToken().getToken());
		Map<String, String> requestMap=new HashMap<String, String>();
		requestMap.put("media_id", media_id);
		HttpURLConnection conn=HttpCon.doPostHttpURLConn(url, Jsoner.toJSON(requestMap), null , null, true);
		
		InputStream in = conn.getInputStream();
		String currentDate=new Date().getTime()+"";
		File imageFile = new File(Constant.uploadDirectory+"/"+currentDate+".jpg");
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(imageFile, false));
			byte[] buffer = new byte[1024];
			int length = 0;

			while ((length = in.read(buffer)) > 0) {
				out.write(buffer , 0 , length);
			}

		}  finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
			if(conn != null){
				conn.disconnect();
			}
		}
		return "upload/"+currentDate+".jpg";
	}
	
	/**
	 * 获取图文群发总数据
	 * @return 0表示成功，其他值表示失败
	 */
	public JSONObject getArticleTotal(String date) {
		// 拼装创建菜单的url
		String url = WechatPublicConfig.Get_Article_Total.replace("ACCESS_TOKEN", getAccessToken().getToken());
		//设置参数
		Map<String, String> paraMap=new HashMap<String, String>();
		paraMap.put("begin_date", date);
		paraMap.put("end_date", date);
		// 调用接口创建菜单
		JSONObject jsonObject = Jsoner.toObject(HttpCon.doPost(url, Jsoner.toJSON(paraMap) , null, null, true));
		return jsonObject;
	}
}
