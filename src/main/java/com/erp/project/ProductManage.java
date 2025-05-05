package com.erp.project;

import com.alibaba.fastjson2.JSONObject;
import com.erp.project.dto.QuerySkuParamsDto;

import java.util.List;

import static com.erp.common.HttpClientCommon.getRequestUrl;
import static com.erp.common.HttpClientCommon.postHttpClient;

public class ProductManage {

    private static final String module = "product";

    public static JSONObject querySku(List<String> skuCodes){

        String requestUrl = getRequestUrl(module, "product_sku_info");
        QuerySkuParamsDto querySkuParamsDto = new QuerySkuParamsDto();
        querySkuParamsDto.skuCodes.addAll(skuCodes);
        querySkuParamsDto.sort.add("{field: \"id\", order: \"desc\"}");

        return postHttpClient(requestUrl, querySkuParamsDto.toString());


    }

}
