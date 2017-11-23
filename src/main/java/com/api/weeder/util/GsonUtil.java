package com.api.weeder.util;

import com.google.gson.Gson;

public class GsonUtil {
	private static final Gson gson = new Gson();

	public static <T> T fromJson(final String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}
}
