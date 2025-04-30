package com.erp.purchase;

import com.alibaba.fastjson2.JSONObject;
import com.wechermer.dto.PurchaseBillDto;

import static com.wechermer.common.ConnectionMysql.executeSql;
import static com.wechermer.common.DateHandle.getDate;
import static com.wechermer.common.HttpClientExample.*;
import static com.wechermer.common.OperateYml.writerEnvironmentVariable;
import static com.wechermer.enumeration.EnvironmentVariableEnum.*;

public class CreatePurchaseBill extends PurchaseBillDto {

    private static String newApiPath;
    private static final String moduleName = "purchase";
    private static final String oldOrderId = "5359";

    // 生成采购单
    public static void purchaseBill(Integer warehouseId) {
        purchaseBillParams = purchaseBillParams.replace("7169", APPLY_PURCHASE_BILL_ID.getValue())
                .replace("QG25031100051", APPLY_PURCHASE_BILL_CODE.getValue())
                .replace("2025-03-12", getDate(3))
                .replace("2025-03-21", getDate(8))
                .replace("2025-06-11",getDate(90));

        if (warehouseId != 2) {
            purchaseBillParams = purchaseBillParams.replace("小飞", "小李1")
                    .replace("17620865451","176201232411")
                    .replace("Detail Address","广州")
                    .replace("\"warehouseId\": 2","\"warehouseId\": " + warehouseId);
        }

        JSONObject orderResponseJson = postHttpClient(getRequestUrl(moduleName,"purchase_order"), purchaseBillParams);

        writerEnvironmentVariable("purchaseOrderId", JSONObject.parseObject(orderResponseJson.getString("result")).getString("id"));
    }

    // 采购单商品明细维护
    public static void purchaseBillGoodsDetails() {
        purchaseBillGoodsDetailsParams = purchaseBillGoodsDetailsParams.replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        postHttpClient(getRequestUrl(moduleName,"purchase_order_detail"), purchaseBillGoodsDetailsParams);
    }

    // 采购单大货明细维护
    public static void purchaseBillBigProductsDetails() {
        purchaseBillBigProductsDetailsParams = purchaseBillBigProductsDetailsParams.replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        postHttpClient(getRequestUrl(moduleName,"purchase_order_big_products"), purchaseBillBigProductsDetailsParams);
    }

    // 采购单附件明细维护
    public static void purchaseBillAttachmentDetails() {
        purchaseBillAttachmentDetailsParams = purchaseBillAttachmentDetailsParams.replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        postHttpClient(getRequestUrl(moduleName,"purchase_order_attachment"), purchaseBillAttachmentDetailsParams);
    }

    // 提交采购单审核
    public static void purchaseBillReview() {
        newApiPath = getRequestUrl(moduleName,"purchase_order_review")
                .replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        putHttpClient(newApiPath, "");
    }

    // 获得采购单code
    public static void purchaseBillCode(){
        newApiPath = getRequestUrl(moduleName,"purchase_order_code")
                .replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        JSONObject resultJson = JSONObject.parseObject(getHttpClient(newApiPath).getString("result"));
        writerEnvironmentVariable("purchaseOrderCode", resultJson.getString("purchaseOrderCode"));
        int approveStatus = resultJson.getIntValue("approveStatus");
        int purchaseOrderStatus = resultJson.getIntValue("purchaseOrderStatus");
        if (approveStatus == 2 || purchaseOrderStatus == 1) {
            String selectSentence = String.format("update `wecharmer.purchase`.purchaseorder set approveStatus = 3 , purchaseOrderStatus = 2 where id = %s" , PURCHASE_ORDER_ID.getValue());
            executeSql(selectSentence);
        }
    }

    // 获得采购单商品明细ID的List
    public static void purchaseOrderGoodsIdList(){
        newApiPath = getRequestUrl(moduleName,"purchase_order_goods")
                .replace(oldOrderId, PURCHASE_ORDER_ID.getValue());
        JSONObject resultJson = JSONObject.parseObject(getHttpClient(newApiPath).getString("result"));

    }

    public static void createPurchaseBill(Integer warehouseId){
        try {
            Thread.sleep(1000);
            purchaseBill(warehouseId);
            Thread.sleep(1000);
            purchaseBillGoodsDetails();
            Thread.sleep(1000);
            purchaseBillBigProductsDetails();
            Thread.sleep(1000);
            purchaseBillAttachmentDetails();
            Thread.sleep(1000);
            purchaseBillReview();
            Thread.sleep(1000);
            purchaseBillCode();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

}
