package com.api.weeder.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.api.weeder.model.APIRequest;
import com.google.gson.JsonObject;

import io.parallec.core.ParallecHeader;
import io.parallec.core.ParallelClient;
import io.parallec.core.bean.HttpMeta;
import io.parallec.core.bean.ResponseHeaderMeta;
import io.parallec.core.config.ParallecGlobalConfig;
import io.parallec.core.config.ParallelTaskConfig;
import io.parallec.core.resources.HttpClientStore;
import io.parallec.core.resources.HttpClientType;

public abstract class AbstractRequestProcessor implements IRequestProcessor {
	/** The log response interval. */
	public static int logResponseInterval = 1;

	/** The log all response after percent. */
	public static double logAllResponseAfterPercent = 95.0;

	/** The log all response before percent. */
	public static double logAllResponseBeforePercent = 5.0;

	/** The log all response before init count. */
	public static int logAllResponseBeforeInitCount = 2;

	/** The log all response if total less than. */
	public static int logAllResponseIfTotalLessThan = 11;

	protected final APIRequest apiRequest;
	protected final URL apiURL;
	protected final ParallelClient parallelClient;
	protected static final ParallelTaskConfig ptconfig;
	protected final HttpMeta httpMeta;

	static {
		ParallecGlobalConfig.ningFastClientConnectionTimeoutMillis = 5000;
		ParallecGlobalConfig.ningFastClientRequestTimeoutMillis = 15000;
		ptconfig = new ParallelTaskConfig();
		ptconfig.setActorMaxOperationTimeoutSec(20);
		ptconfig.setAutoSaveLogToLocal(true);
		ptconfig.setSaveResponseToTask(true);
		ptconfig.setTimeoutInManagerSec(900);
		ptconfig.setTimeoutAskManagerSec(910);
	}

	public AbstractRequestProcessor(final APIRequest apiRequest) throws MalformedURLException {
		HttpClientStore.getInstance().setHttpClientTypeCurrentDefault(HttpClientType.CUSTOM_FAST);
		this.parallelClient = new ParallelClient();
		this.apiRequest = apiRequest;
		this.apiURL = new URL(apiRequest.getUrl());
		this.httpMeta = new HttpMeta();
		this.httpMeta.setResponseHeaderMeta(new ResponseHeaderMeta(null, true));
	}

	protected String getUrl() {
		return apiURL.toString();
	}

	protected String getHost() {
		return apiURL.getHost();
	}

	protected int getPort() {
		if ("HTTPS".equalsIgnoreCase(getProtocol()))
			return 443;
		else if (apiURL.getPort() == -1)
			return apiURL.getDefaultPort();
		return apiURL.getPort();
	}

	protected String getProtocol() {
		return apiURL.getProtocol().toUpperCase();
	}

	protected String getPath() {
		return apiURL.getPath() + "?" + getQueryParam();
	}

	protected String getQueryParam() {
		return apiURL.getQuery();
	}

	protected ParallecHeader getHeaders() {
		final Map<String, String> requestHeaders = apiRequest.getHeaders();
		ParallecHeader headers = null;
		if (null != requestHeaders && !requestHeaders.isEmpty()) {
			headers = new ParallecHeader();
			for (Entry<String, String> entry : requestHeaders.entrySet()) {
				headers.addPair(entry.getKey(), entry.getValue());
			}
		}
		return headers;
	}

	protected String getPayload() {
		final JsonObject body = apiRequest.getBody();
		if (null != body && !body.entrySet().isEmpty()) {
			return body.toString();
		}
		return new JsonObject().toString();
	}

	public void doClose() {
		parallelClient.releaseExternalResources();
	}
}
