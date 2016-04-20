package cn.diffpi.rop.wechat.token;

import java.util.HashMap;
import java.util.Map;

public class TokenCache {
	private Map<String , AccessToken> tokenMap = new HashMap<String, AccessToken>();
	
    public void put(AccessToken token){
    	tokenMap.put(token.getTokenId(), token);
    }
	
	public void remove(String tokenId){
		tokenMap.remove(tokenId);
	}
	
	public boolean contains(AccessToken token){
		return tokenMap.containsKey(token.getTokenId());
	}
	
	public Map<String , AccessToken> getAll(){
		return tokenMap;
	}
	
	public AccessToken get(String tokenId){
		return tokenMap.get(tokenId);
	}
}
