package cn.diffpi.rop.sms.util;


public class UcpaasInfo {
	/***
	 * 开发者id
	 */
	public static final String ACCOUNT_SID = "83580fe66130e62ed0208789e68668df";
	/***
	 * 授权token
	 */
	public static final String AUTH_TOKEN = "ba2b89f37780e115cc56f0062c5460d6";
	/***
	 * 访问路径
	 */
	public static final String HOST_REST_URL = "https://api.ucpaas.com";

	/***
	 * APP_ID
	 */
	public static final String APP_ID = "fcefc96b72234227bf54e5136230c6bc";

	/***
	 * 请求地址
	 */
	public static final String JSON_URL = "/2014-06-30/Accounts/{0}/Messages/templateSMS?sig={1}";
	


	/***
	 * 欢迎使用免费wifi上网系统，验证码为：{1}
	 */
	public static final String TEMPLATE_ID = "22420";

	/***
	 *普通模板id   亲爱的用户，您有新的提醒：{1} 。可以登陆洁美仕应用进行查看。 
	 */
	public static final String CUS_TEMPLATE_ID = "";
	
	/***
	 *找回密码模板id   亲爱的用户，您的验证码为：{1},请于{2}分钟内正确输入验证码。（此验证码不要告诉任何人）
	 */
	public static final String UP_PWD_ID = "12715";

	private static final String $1 = "洁美仕";// 参数1
	public static final String $3 = "3";// 参数3

	/***
	 * 得到模板参数
	 */
	public static final String[] getParams(String code) {
		String[] params = new String[3];
		params[0] = $1;
		params[1] = code;
		params[2] = $3;

		return params;
	}

}
