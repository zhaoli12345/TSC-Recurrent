package com.cdut.current.common;

import lombok.Data;

@Data
public class ServiceResult<T> {
    public static final int STATE_SUCCESS = 0;
    public static final int STATE_APP_EXCEPTION = 1;

    private T result;

    private Object error;

    private int state;

    public ServiceResult() {
    }

    public ServiceResult(T result, int state) {
        this.result = result;
        this.state = state;
    }

    public static <T> ServiceResult<T> success(T result) {
        return new ServiceResult<>(result, 0);
    }

    public static ServiceResult<String> success() {
        return new ServiceResult<>("NO_MESSAGE", 0);
    }

    public static <T> ServiceResult<T> failure(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, 1);
        f.setError(result);
        return f;
    }
    public static <T> ServiceResult<T> failure(Object error, T result) {
        ServiceResult<T> f = new ServiceResult<>(result, 1);
        f.setError(error);
        return f;
    }

    public String toString() {
        return "ServiceResult{result=" + this.result + ", error=" + this.error + ", state=" + this.state + '}';
    }
}
