package cn.diffpi.rop.wechat.info.config;

public class WechatEnterpriseConfig {
	//获取部门列表
	public static final String GET_DEPARTMENT_LIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
	//获取部门成员
	public static final String GET_EMPLOYEE_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID";
	//获取accesstoken
	public static final String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
	//创建菜单
	public static final String MENU_CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";
	// 微信OAuth2.0授权 snsapi_base方式
	// 通过code换取网页授权access_token
	public static final String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public static final String GET_USER_INFO= "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	//根据openid获取userid
	public static final String GET_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=ACCESS_TOKEN";
	//发送消息url
	public static final String SEND_MSG = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	//上传临时素材
	public static final String UPLOAD_FILE = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String GET_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";
	//获取素材
	public static final String Get_Material_List = "https://qyapi.weixin.qq.com/cgi-bin/material/batchget?access_token=ACCESS_TOKEN";
	//获取临时素材
	public static final String Get_Temp_Material = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	//获取企业号应用 
	public static final String GET_AGENT = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
	//创建标签
	public static final String Create_Tag = "https://qyapi.weixin.qq.com/cgi-bin/tag/create?access_token=ACCESS_TOKEN";
	//添加标签人员
	public static final String ADD_TAG_USERS = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?access_token=ACCESS_TOKEN";
	//获取标签列表
	public static final String GET_TAGS = "https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN";
	//删除标签
	public static final String DELETE_TAGS = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?access_token=ACCESS_TOKEN&tagid=TAGID";
	
	/***************************************************企业号配置**************************************************************/
	public static final String CORPID = "wxee9e4dcd65a1ce39";
	public static final String SECRET = "QIRAj0Yn6-1QtrB9ifqewSAircspAfT97s2m2OTz8MEkVQS4QtpxyIYUAEgbXdcd";
	public static final String AGENTID = "39";//同事圈
	
	
	/*public static final String CORPID = "wxed1e44a4d9e9c112";
	public static final String SECRET = "0cxQM2zqlK2c3UXwddscnBBwK3J50Po7w5c4Gnu_oKsWm4gCu0gAH3yjdSuXLcsR";
	public static final String AGENTID = "2";//同事圈
*/
	/***
	 * 网页授权得到openid
	 * 
	 * @param code
	 * 页面由微信get方式发送
	 * @param appId
	 * 凭证
	 * @return 授权路径
	 */
	public static String getEnterpriseOauthUrl(String redirectUrl, String state , String corpid) {
		// 拼接授权url
		String url = WechatEnterpriseConfig.OAUTH_URL.replace("CORPID", corpid).replace("REDIRECT_URI", redirectUrl).replace("STATE", state);
		return url;
	}
}
