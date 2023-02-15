package com.ilsmp.base.rpc.request;

import com.ilsmp.base.rpc.response.MarketplaceListResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MarketplaceListRequest extends Request<MarketplaceListResponse> {
    private String keyword;

    private Integer projectType;

    private String organizationName;

    private String createName;

    private Integer pageNumber = 1;
    private Integer pageSize = 20;

    @Override
    public String getPath() {
        return "/api/crud-admin/marketplace/list";
    }

    @Override
    public Class<MarketplaceListResponse> getResponseClass() {
        return MarketplaceListResponse.class;
    }
}
