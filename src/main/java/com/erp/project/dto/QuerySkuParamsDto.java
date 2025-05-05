package com.erp.project.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QuerySkuParamsDto {

    public Integer entityInfoType = 1;
    public Integer pageIndex = 1;
    public Integer pageSize = 10;
    public ArrayList<String> skuCodes = new ArrayList<>();
    public ArrayList<Object> sort = new ArrayList<>();

}
