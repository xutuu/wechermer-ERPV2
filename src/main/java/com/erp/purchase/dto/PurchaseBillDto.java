package com.erp.purchase.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PurchaseBillDto {

    private Integer companyId = 3;
    private Integer supplierId = 471;
    private Integer warehouseId = 2;
    private Integer purchaseOrderType = 1;
    private Integer productDevelopType = 1;
    private Integer supplierPaymentMethod = 1;
    private Integer supplierAccountId = 415;
    private Integer operateDivisionId = 2;
    private Integer shopId = 9;
    private String remark;
    private Double logisticsFee = 0.0;
    private ArrayList<String> attachments ;
    private Integer purchaserId = 329;
    private String deliveryDate;
    private String planDeliveryDate;
    private String companyCurrencyType;
    private Double exchangeRate = 7.1745;
    private Double exchangeRateLimit = 7.1745;
    private String supplierContactPerson = "微城开";
    private String supplierContactPersonMobile = "18745125748";
    private Integer supplierSettlementMethod = 3;
    private Integer supplierSettlementDay = 30;
    private Double supplierPrepaidRate = 0.5;
    private Integer purchaseSourceType = 4;
    private String purchaseOrderCode;
    private String purchasePlanBillId;
    private Object skuSupplierObj;
    private String bhApplyPurchaseBillId;
    private Integer operaterId = 329;
    private String operaterName = "徐陆强";
    private String bhPurchaseBillId;
    private String bhPurchaseBillCode;
    private Integer productGroupId = 11;
    private Integer developDivisionId = 2;
    private String applyPurchaseBillCode;
    private String applyPurchaseBillId;
    private String soureBillId;
    private String expectedArrivalTime;
    private String expectedPutOnSaleTime;
    private String chargePerson = "小飞";
    private String chargePersonMobile = "17620865451";
    private String address = "Detail Address";
    private String supplierOpeningBank = "微城开";
    private String supplierAccountNumber = "1578421226599";
    private String developDivisionName = "开发户外事业部";
    private String productGroupName = "管理员组";

}
