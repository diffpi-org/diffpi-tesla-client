package cn.diffpi.response;

import java.util.Locale;

import com.alibaba.fastjson.JSONObject;

import cn.diffpi.response.errresp.ErrorResponse;
import cn.diffpi.response.errresp.NotExistErrorResponse;
import cn.diffpi.response.errresp.RejectedServiceResponse;
import cn.diffpi.response.errresp.ServiceUnavailableErrorResponse;
import cn.diffpi.response.errresp.TimeoutErrorResponse;
import cn.dreampie.common.http.exception.WebException;
import cn.dreampie.common.http.result.HttpStatus;

public class ErrModelException extends WebException {
	private static final long serialVersionUID = 1L;
	private ErrorResponse errorResponse;

	public ErrModelException(HttpStatus status, ErrorResponse errorResponse , String message) {
		super(status , message);
		this.errorResponse = errorResponse;
	}

	public static ErrModelException errorResponse(ErrorResponse errorResponse) {
		ErrorResponse response = errorResponse;

		return build(response);
	}

	public static ErrModelException notExistError(String objectName, String queryFieldName, Object queryFieldValue, Locale locale) {
		ErrorResponse response = new NotExistErrorResponse(objectName, queryFieldName, queryFieldValue, locale);

		return build(response);
	}

	public static ErrModelException businessServiceError() {

		return null;
	}

	public static ErrModelException Common(Boolean bool) {
		return null;
	}

	public static ErrModelException rejectedService(String method, String version, Locale locale) {
		ErrorResponse response = new RejectedServiceResponse(method, version, locale);

		return build(response);
	}

	public static ErrModelException serviceUnavailableError(String method, Locale locale) {
		ErrorResponse response = new ServiceUnavailableErrorResponse(method, locale);

		return build(response);
	}

	public static ErrModelException serviceUnavailableError(String method, Locale locale, Throwable throwable) {
		ErrorResponse response = new ServiceUnavailableErrorResponse(method, locale, throwable);

		return build(response);
	}
	
	public static ErrModelException timeoutError(String method, Locale locale, int timeout) {
		ErrorResponse response = new TimeoutErrorResponse(method, locale , timeout);

		return build(response);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	private static ErrModelException build(ErrorResponse response){
		return new ErrModelException(HttpStatus.INTERNAL_SERVER_ERROR, response , JSONObject.toJSONString(response).toString());
	}
}
