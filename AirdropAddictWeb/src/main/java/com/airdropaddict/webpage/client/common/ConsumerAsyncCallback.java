package com.airdropaddict.webpage.client.common;

import java.util.function.Consumer;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ConsumerAsyncCallback<T> implements AsyncCallback<T> {
	Consumer<Throwable> failureHandler;
	Consumer<T> successHandler;

	public static <T> ConsumerAsyncCallback<T> callback(Consumer<T> successHandler) {
		return new ConsumerAsyncCallback<T>(successHandler);
	}

	public ConsumerAsyncCallback(Consumer<Throwable> failureHandler, Consumer<T> successHandler) {
		this.failureHandler = failureHandler;
		this.successHandler = successHandler;
	}

	public ConsumerAsyncCallback(Consumer<T> successHandler) {
		this(t -> Window.alert("Request failure: " + t.getMessage()), successHandler);
	}

	@Override
	public void onFailure(Throwable throwable) {
		failureHandler.accept(throwable);
	}

	@Override
	public void onSuccess(T t) {
		successHandler.accept(t);
	}
}
