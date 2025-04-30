package com.erp.purchase.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CreateApplyPurchaseBillParamsDto {

    private Integer shopId = 9;
    private Integer applyPurchaseType = 1;
    private String productDevelopRequireBillId;
    private Integer operateDivisionId = 2;
    private String remark;
    private String expectedArrivalTime;
    private String expectedPutOnSaleTime;
    private Integer developType = 1;
    private ArrayList<String> sampleUsageIds;
    private Integer purchaseBusinessType = 3;
    private Integer expectedShipmentAddr = 1;
    private Boolean isQuickReturn = false;
    private Integer operaterId = 329;
    private String operaterName = "徐陆强";
    private ArrayList<String> attachments;
    private Integer warehouseType = 2;
    private Integer transportationTypeId = 19;
    private String transportationTypeName;
    private Integer stockInType = 4;
    private Integer productGroupId = 11;
    private Integer developDivisionId = 2;

}
