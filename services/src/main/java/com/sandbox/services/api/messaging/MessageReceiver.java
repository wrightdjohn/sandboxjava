package com.sandbox.services.api.messaging;

public interface MessageReceiver<T> {
    T receive();
}
