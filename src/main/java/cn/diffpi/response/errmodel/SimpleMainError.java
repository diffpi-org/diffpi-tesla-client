package cn.diffpi.response.errmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能说明：
 * </pre>
 */
public class SimpleMainError implements MainError {

	private String code;

	private String message;

	private String solution;

	private List<SubError> subErrors = new ArrayList<SubError>();

	public SimpleMainError(String code, String message, String solution) {
		this.code = code;
		this.message = message;
		this.solution = solution;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getSolution() {
		return solution;
	}

	public List<SubError> getSubErrors() {
		return this.subErrors;
	}

	public void setSubErrors(List<SubError> subErrors) {
		this.subErrors = subErrors;
	}

	public MainError addSubError(SubError subError) {
		this.subErrors.add(subError);
		return this;
	}
}
