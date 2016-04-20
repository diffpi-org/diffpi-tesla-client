package cn.diffpi.rop.wechat.token;

import java.text.MessageFormat;

import cn.diffpi.kit.HttpCon;
import cn.dreampie.common.util.json.Jsoner;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class TokenManager {
	
	private static final TokenCache TOKEN_CACHE = new TokenCache();
	
	private static AccessToken craeteToken(String access_token_url , String tokenId , String... params) throws Exception{
		AccessToken accessToken = null;
		
		if(!validateToken(tokenId)){//验证accessToken 是否过期
			String requestUrl = MessageFormat.format(access_token_url, params);
			
			JSONObject jsonObject = Jsoner.toObject(HttpCon.doGet(requestUrl, null, null, null, true));
			
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
					accessToken.setCreatetime(System.currentTimeMillis());
					
					TOKEN_CACHE.put(accessToken);
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
				}
			}
		}else{
			accessToken = TOKEN_CACHE.get(tokenId);
		}
		
		return accessToken;
	}
	
	public static synchronized boolean validateToken(String tokenId) {
		boolean tokenValidate = true;
		if(tokenId != null && TOKEN_CACHE.get(tokenId) != null){
			long createtime = TOKEN_CACHE.get(tokenId).getCreatetime();
			long currenttime = System.currentTimeMillis();
			
			int time_difference = (int)(currenttime - createtime);
			if(time_difference/1000 >= TOKEN_CACHE.get(tokenId).getExpiresIn()){
				tokenValidate = false;
				TOKEN_CACHE.remove(tokenId);//移除过期的token
			}
		}else{
			tokenValidate = false;
		}
		
		return tokenValidate;
	}
	
	public static AccessToken getAccessToken(String access_token_url , String appId , String... params) throws Exception {
		return craeteToken(access_token_url, appId, params);
	}
}
