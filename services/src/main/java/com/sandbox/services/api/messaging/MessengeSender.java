package com.sandbox.services.api.messaging;

public interface MessengeSender<T> {
    void send(T data);
}
