package com.spring.firstthymeleafapp.utils;

/**
 * Container class for holding the URI and URLs requested by calling clients.
 *
 */
public class RequestedResource {

	private String requestUri;
	
	private String requestUrl;

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
