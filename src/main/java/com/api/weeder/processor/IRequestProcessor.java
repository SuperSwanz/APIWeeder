package com.api.weeder.processor;

public interface IRequestProcessor {
	public void doGet();

	public void doPost();

	public void doPut();

	public void doDelete();

	public void doOptions();

	public void doClose();
}
