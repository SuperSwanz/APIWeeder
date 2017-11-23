package com.api.weeder.processor;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.api.weeder.model.APIRequest;

import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.RequestProtocol;
import io.parallec.core.ResponseOnSingleTask;
import io.parallec.core.bean.ResponseHeaderMeta;

public class RequestProcessor extends AbstractRequestProcessor {

	public RequestProcessor(final APIRequest apiRequest) throws MalformedURLException {
		super(apiRequest);
	}

	@Override
	public void doGet() {
		parallelClient
		.prepareHttpGet(getPath())
		.saveResponseHeaders(new ResponseHeaderMeta(null, true))
		.setTargetHostsFromString(getHost())
		.setProtocol(RequestProtocol.valueOf(getProtocol()))
		.setHttpPort(getPort())
		.setConfig(ptconfig)
		.setHttpHeaders(getHeaders())
		.execute(new ParallecResponseHandler() {
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println(res.toString());
				Map<String, List<String>> responseHeaders = res.getResponseHeaders();
				for(Entry<String, List<String>> entry: responseHeaders.entrySet()){
                    System.out.println(entry.getKey() + "::" + entry.getValue());
                }
				responseContext.put(res.getHost(), responseHeaders.size());
			}
		})
		.setHttpMeta(httpMeta);
	}

	@Override
	public void doPost() {
		parallelClient
		.prepareHttpPost(getPath())
		.saveResponseHeaders(new ResponseHeaderMeta(null, true))		
		.setTargetHostsFromString(getHost())
		.setProtocol(RequestProtocol.valueOf(getProtocol()))
		.setHttpPort(getPort())
		.setConfig(ptconfig)
		.setHttpHeaders(getHeaders())
		.setHttpEntityBody(getPayload())
		.execute(new ParallecResponseHandler() {
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println(res.toString());
			}
		});
	}

	@Override
	public void doPut() {
		parallelClient
		.prepareHttpPut(getPath())
		.saveResponseHeaders(new ResponseHeaderMeta(null, true))		
		.setTargetHostsFromString(getHost())
		.setProtocol(RequestProtocol.valueOf(getProtocol()))
		.setHttpPort(getPort())
		.setConfig(ptconfig)
		.setHttpHeaders(getHeaders())
		.setHttpEntityBody(getPayload())
		.execute(new ParallecResponseHandler() {
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println(res.toString());
			}
		})
		.setHttpMeta(httpMeta);
	}

	@Override
	public void doDelete() {
		parallelClient
		.prepareHttpDelete(getPath())
		.saveResponseHeaders(new ResponseHeaderMeta(null, true))		
		.setTargetHostsFromString(getHost())
		.setProtocol(RequestProtocol.valueOf(getProtocol()))
		.setHttpPort(getPort())
		.setConfig(ptconfig)
		.setHttpHeaders(getHeaders())
		.setHttpEntityBody(getPayload())
		.execute(new ParallecResponseHandler() {
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println(res.toString());
			}
		})
		.setHttpMeta(httpMeta);
	}

	@Override
	public void doOptions() {
		parallelClient
		.prepareHttpOptions(getPath())
		.saveResponseHeaders(new ResponseHeaderMeta(null, true))		
		.setTargetHostsFromString(getHost())
		.setProtocol(RequestProtocol.valueOf(getProtocol()))
		.setHttpPort(getPort())
		.setConfig(ptconfig)
		.setHttpHeaders(getHeaders())
		.setHttpEntityBody(getPayload())
		.execute(new ParallecResponseHandler() {
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println(res.toString());
			}
		})
		.setHttpMeta(httpMeta);
	}
}