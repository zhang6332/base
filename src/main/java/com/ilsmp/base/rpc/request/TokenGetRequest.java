package com.ilsmp.base.rpc.request;

import java.util.HashMap;
import java.util.Map;

import com.ilsmp.base.rpc.response.ProjectTemplateResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TokenGetRequest extends Request<ProjectTemplateResponse> {
    private String accessToken;

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public Map<String, String> getQuery() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        return map;
    }

    @Override
    public String getPath() {
        return "/api/crud-admin/plugin/getTemplate";
    }

    @Override
    public Class<ProjectTemplateResponse> getResponseClass() {
        return ProjectTemplateResponse.class;
    }
}