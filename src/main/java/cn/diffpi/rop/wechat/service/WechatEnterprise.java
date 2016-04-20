package cn.diffpi.rop.wechat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.diffpi.kit.HttpCon;
import cn.diffpi.rop.wechat.course.menu.Menu;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.UserBaseMessage;
import cn.diffpi.rop.wechat.course.message.offer.mass.openid.text.OneTextType;
import cn.diffpi.rop.wechat.info.config.WechatEnterpriseConfig;
import cn.diffpi.rop.wechat.ticket.JsApiTicket;
import cn.diffpi.rop.wechat.ticket.TicketManager;
import cn.diffpi.rop.wechat.token.AccessToken;
import cn.diffpi.rop.wechat.token.TokenManager;
import cn.dreampie.common.util.json.Jsoner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WechatEnterprise {

	private String corpid = null;
	private String secret = null;
	private String agentid = null;

	public WechatEnterprise(String corpid, String secret, String agentid) {
		this.corpid = corpid;
		this.secret = secret;
		this.agentid = agentid;
	}

	private AccessToken getAccessToken() {
		try {
			String url = WechatEnterpriseConfig.TOKEN_URL.replace("CORPID", "{0}").replace("CORPSECRET", "{1}");
 
			return TokenManager.getAccessToken(url, corpid, corpid, secret);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new AccessToken();
	}
	
	/***
	 * 获取jsapiticket
	 * @return
	 */
	public JsApiTicket getJsApiTicket(){
		String ticket_url = WechatEnterpriseConfig.GET_JSAPI_TICKET.replace("ACCESS_TOKEN", getAccessToken().getToken());
	
		try {
			return TicketManager.getJsApiTicket(ticket_url, corpid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new JsApiTicket();
	}

	/**
	 * 创建菜单
	 * @param menu
	 * 菜单实例
	 * @param accessToken
	 * 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public JSONObject createMenu(Menu menu) {
		// 拼装创建菜单的url
		String url = WechatEnterpriseConfig.MENU_CREATE_URL.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("AGENTID", agentid);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.toJSONString(menu);
		// 调用接口创建菜单

		JSONObject jsonObject = Jsoner.toObject(HttpCon.doPost(url, jsonMenu, null, null, true));

		return jsonObject;
	}

	/***
	 * 根据code获取用户信息
	 * 企业成员授权时返回UserId
	 * 非企业成员授权时返回OpenId
	 * @param code
	 * @return
	 */
	public JSONObject getUserInfo(String code){
		String url = WechatEnterpriseConfig.GET_USER_INFO.replace("CODE", code).replace("ACCESS_TOKEN",this.getAccessToken().getToken());
		JSONObject jsonObject =  Jsoner.toObject(HttpCon.doGet(url, null, null, null, true));
		
		if(jsonObject != null){
			try {
				return jsonObject;
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取部门列表
	 */
	public JSONArray getDepartmentList() {
		String url = WechatEnterpriseConfig.GET_DEPARTMENT_LIST.replace("ACCESS_TOKEN", this.getAccessToken().getToken());
		JSONObject jsonObj = Jsoner.toObject(HttpCon.doGet(url, null, null, null, true));
		if (jsonObj != null) {
			return (JSONArray) jsonObj.get("department");
		}
		return null;
	}

	/**
	 * 获取部门的成员列表
	 * department_id 部门id
	 * fetch_child 1/0：是否递归获取子部门下面的成员
	 * status 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 */
	public JSONArray getEmployeeList(Integer department_id, Integer fetch_child, Integer status) {
		if (department_id == null) {
			return null;
		}

		String url = WechatEnterpriseConfig.GET_EMPLOYEE_LIST.replace("ACCESS_TOKEN", this.getAccessToken().getToken()).replace("DEPARTMENT_ID", department_id.toString());
		if (fetch_child != null) {
			url = url + "&fetch_child=" + fetch_child;
		}

		if (status != null) {
			url = url + "&status=" + status;
		}

		JSONObject jsonObj = Jsoner.toObject(HttpCon.doGet(url, null, null, null, true));
		if (jsonObj != null) {
			return (JSONArray) jsonObj.get("userlist");
		}
		return null;
	}
	
	/**
	 * 根据openid获取userid
	 */
	public String getUserid(String openid){
		String url = WechatEnterpriseConfig.GET_USERID.replace("ACCESS_TOKEN", this.getAccessToken().getToken());
		JSONObject jobject = Jsoner.toObject(HttpCon.doPost(url, openid, null, null, true));
		if(jobject != null){
			return jobject.get("userid").toString();
		}
		return null;
	}
	
	/**
	 * 发送信息
	 * @param mnm
	 * @return 0表示成功，其他值表示失败
	 */
	public int sendOneNews(UserBaseMessage mnm) {
		int result = 0;
		// 拼装创建菜单的url
		String url = WechatEnterpriseConfig.SEND_MSG.replace("ACCESS_TOKEN", getAccessToken().getToken());
		// 将菜单对象转换成json字符串
		String textToSend = Jsoner.toJSON(mnm);
		// 调用接口创建菜单
		JSONObject jsonObject = Jsoner.toObject(HttpCon.doPost(url, textToSend, null, null, true));

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
			}
		}
		return result;
	}
	
	/**
	 * 发送最新素材
	 * @param todaysnews
	 * @return 0表示成功，其他值表示失败
	 */
	public int sendOneText(OneTextType oneTextType) {
		int result = 0;
		// 拼装创建菜单的url
		String url = WechatEnterpriseConfig.SEND_MSG.replace("ACCESS_TOKEN", getAccessToken().getToken());
		// 将菜单对象转换成json字符串
		String textToSend = Jsoner.toJSON(oneTextType);
		// 调用接口创建菜单
		JSONObject jsonObject = Jsoner.toObject(HttpCon.doPost(url, textToSend, null, null, true));

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
			}
		}
		return result;
	}
	
	/**
	 * 发送最新素材
	 * @param todaysnews
	 * @return 0表示成功，其他值表示失败
	 */
	public String uploadFile(File file) {
		String media_id = "";
		// 拼装创建菜单的url
		String url = WechatEnterpriseConfig.UPLOAD_FILE.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("TYPE", "image");
		//设置参数
//		Map<String, String> paraMap=new HashMap<String, String>();
//		paraMap.put("", value)
		Map<String, List<File>> fileMap=new HashMap<String, List<File>>();
		List<File> files=new ArrayList<File>();
		files.add(file);
		fileMap.put("media", files);
		// 调用接口创建菜单
		JSONObject jsonObject = Jsoner.toObject(HttpCon.doPostFile(url, null, fileMap, null, true));

		if (null != jsonObject) {
			return media_id=jsonObject.getString("media_id");
		}
		return media_id;
	}
	
	/***
	 * 获取素材列表
	 * @return
	 */
	public JSONObject getMaterialList(int offset){
		String url = WechatEnterpriseConfig.Get_Material_List.replace("ACCESS_TOKEN", getAccessToken().getToken());
		Map<String, Object> requestMap=new HashMap<String, Object>();
		requestMap.put("type", "mpnews");
		requestMap.put("agentid", agentid);
		requestMap.put("offset", offset);
		requestMap.put("count", "20");
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doPost(url, Jsoner.toJSON(requestMap), null, null, true));
		return jsonObj;
	}
	
	/***
	 * 获取企业号应用
	 * @return
	 */
	public JSONObject getAgent(){
		String url  =  WechatEnterpriseConfig.GET_AGENT.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("AGENTID", this.agentid);
		
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doGet(url, null, null, null, true));
		
		return jsonObj;
	}
	
	/***
	 * 创建标签
	 * @return
	 */
	public JSONObject createTag(Map<String, String> tagMap){
		String url  =  WechatEnterpriseConfig.Create_Tag.replace("ACCESS_TOKEN", getAccessToken().getToken());
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doPost(url, Jsoner.toJSON(tagMap), null , null, true));
		return jsonObj;
	}
	
	/***
	 * 删除标签
	 * @return
	 */
	public JSONObject deleteTag(String tagId){
		String url  =  WechatEnterpriseConfig.DELETE_TAGS.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("TAGID", tagId);
		JSONObject jsonObj =   Jsoner.toObject(HttpCon.doPost(url, "", null , null, true));
		return jsonObj;
	}
	
	/***
	 * 添加标签人员
	 * 
	 * @param tagId 标签id
	 * @param users 用户列表
	 * @param partys 部门列表
	 * @return
	 */
	public JSONObject addTagUsers(String tagId , List<String> users , List<String> partys){
		String url = WechatEnterpriseConfig.ADD_TAG_USERS.replace("ACCESS_TOKEN", getAccessToken().getToken());
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tagid", tagId);
		dataMap.put("userlist", users);
		dataMap.put("partylist", partys);
		
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doPost(url, Jsoner.toJSON(dataMap), null, null, true));
		
		return jsonObj;
	}
	
	/***
	 * 获取标签列表
	 * @return
	 */
	public JSONObject getTags(){
		String url  =  WechatEnterpriseConfig.GET_TAGS.replace("ACCESS_TOKEN", getAccessToken().getToken());
		
		JSONObject jsonObj =  Jsoner.toObject(HttpCon.doGet(url, null, null, null, true));
		
		return jsonObj;
	}
}
