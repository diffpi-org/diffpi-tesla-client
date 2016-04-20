package cn.diffpi.rop.wechat.info.config;


/**
 * 公众平台通用接口工具类
 * 
 * @author luoxun
 */
public class WechatPublicConfig {
	/*******************************接口url****************************/
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 微信OAuth2.0授权 snsapi_base方式
	// 通过code换取网页授权access_token的url
	public static String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	public static String GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 微信群发接口 (每个微信用户每月只能接收4次)
	public static String MSG_MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//根据openid发送客户消息url
	public static String MSG_CUST_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; 
	// 根据openid获取微信个人信息接口
	public static String GET_OPEN_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 上传文件
	public static String UPLOAD_FILE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//图文上传url
	public static String UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	//获取用户列表
	public static String GET_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN ";
	public static String GET_NEXT_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID1";
	//获取上传的视频
	public static String UPLOAD_VIDEO_URL = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN ";
	//获取素材列表
	public static String GET_MATERIALLIST_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	//获取素材
	public static String GET_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	//获取图文群发总数据
	public static final String Get_Article_Total = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN";		
}