package com.sandbox.services.api.dao;

import com.sandbox.services.core.BizId;
import java.util.List;
import java.util.Optional;

public interface Finder<T> {
    Optional<T> findById(BizId id);
    List<T> findBy(Criteria criteria);
}
