package cn.diffpi.response.errmodel;

/**
 * <pre>
 * 功能说明：
 * </pre>
 */
public class SubError {

	private String code;

	private String message;

	public SubError() {
	}

	public SubError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
