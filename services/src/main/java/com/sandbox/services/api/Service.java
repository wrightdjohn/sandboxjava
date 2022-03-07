package com.sandbox.services.api;


public interface Service<T extends ServiceRequest,U extends ServiceResponse> {

    U executeRequest(T request);

}
