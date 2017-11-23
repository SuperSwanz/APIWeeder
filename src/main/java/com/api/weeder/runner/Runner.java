package com.api.weeder.runner;

import static com.api.weeder.util.GsonUtil.fromJson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.api.weeder.client.AbstractRequestProcessor;
import com.api.weeder.client.RequestProcessor;
import com.api.weeder.model.APIRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.parallec.core.resources.HttpMethod;
import scala.NotImplementedError;

public class Runner {
	public static final Path path = Paths.get("request.json");
	public final static String REQUESTS_KEY = "requests";
	public final static String BODY_KEY = "body";

	public static void main(String[] args) {
		AbstractRequestProcessor requestProcessor = null;
		try {
			final String json = new String(Files.readAllBytes(path));
			JsonObject requests = new JsonParser().parse(json).getAsJsonObject();
			JsonArray array = requests.getAsJsonArray(REQUESTS_KEY);
			for (JsonElement jsonElement : array) {
				JsonObject request = jsonElement.getAsJsonObject();
				final JsonObject body = request.getAsJsonObject(BODY_KEY);
				request.remove(BODY_KEY);
				APIRequest apiRequest = fromJson(request.toString(), APIRequest.class);
				requestProcessor = new RequestProcessor(apiRequest);
				if (null != body)
					apiRequest.setBody(body);
				switch (getHttpMethod(apiRequest)) {
				case GET:
					requestProcessor.doGet();
					break;
				case POST:
					requestProcessor.doPost();
					break;
				case OPTIONS:
					requestProcessor.doOptions();
					break;
				case DELETE:
					requestProcessor.doDelete();
					break;
				case PUT:
					requestProcessor.doPut();
					break;
				default:
					throw new NotImplementedError("Http Method not implemented");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != requestProcessor)
				requestProcessor.doClose();
		}
	}

	public static HttpMethod getHttpMethod(final APIRequest apiRequest) {
		return HttpMethod.valueOf(apiRequest.getHttpMethod().toUpperCase());
	}
}