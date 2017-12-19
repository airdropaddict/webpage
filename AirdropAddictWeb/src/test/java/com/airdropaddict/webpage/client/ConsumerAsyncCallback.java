package com.airdropaddict.webpage.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.function.Consumer;

import static com.ibm.icu.impl.Assert.fail;

public class ConsumerAsyncCallback<T> implements AsyncCallback<T>
{

    Consumer<Throwable> failureHandler;
    Consumer<T> successHandler;
    public ConsumerAsyncCallback(Consumer<Throwable> failureHandler, Consumer<T> successHandler) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
    }

    public ConsumerAsyncCallback(Consumer<T> successHandler) {
        this(t -> fail("Request failure: " + t.getMessage()), successHandler);
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