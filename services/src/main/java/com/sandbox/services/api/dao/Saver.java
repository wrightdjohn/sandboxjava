package com.sandbox.services.api.dao;

import java.util.Collection;

public interface Saver<T> {
    T save(T data);
    Collection<T> save(Collection<T> data);
}
