package com.ilsmp.base.rpc.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.ilsmp.base.rpc.response.Response;

public abstract class Request<T extends Response> {
    @JSONField(serialize = false)
    public String getMethod() {
        return "POST";
    }

    @JSONField(serialize = false)
    public String getBody() {
        return "{}";
    }

    @JSONField(serialize = false)
    public Map<String, String> getQuery() {
        return new HashMap<>();
    }

    @JSONField(serialize = false)
    public abstract String getPath();

    @JSONField(serialize = false)
    public abstract Class<T> getResponseClass();

}