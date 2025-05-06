package com.erp.purchase;

import com.alibaba.fastjson2.JSONObject;
import com.erp.purchase.dto.PurchaseBillDto;

import java.util.HashMap;

import static com.erp.common.DateHandle.*;
import static com.erp.common.HttpClientCommon.*;
import static com.erp.common.OperateYml.writerEnvironmentVariable;
import static com.erp.env.EnvironmentVariableEnum.APPLY_PURCHASE_BILL_CODE;
import static com.erp.env.EnvironmentVariableEnum.APPLY_PURCHASE_BILL_ID;

public class CreatePurchaseBill extends PurchaseBillDto {

    private static String newApiPath;
    private static final String moduleName = "purchase";
    private static final String oldOrderId = "5359";

    // 生成采购单
    public static void purchaseBill(Integer warehouseId) {

        HashMap<String, Integer> hashMap = new HashMap<>();

        PurchaseBillDto purchaseBillDto = new PurchaseBillDto();
        purchaseBillDto.setDeliveryDate(getTargetDate(getDate(3), "Z"));
        purchaseBillDto.setExpectedArrivalTime(getTargetDate(getDate(10), "T"));
        purchaseBillDto.setExpectedPutOnSaleTime(getTargetDate(getMonth(3), "T"));
        purchaseBillDto.setApplyPurchaseBillId(APPLY_PURCHASE_BILL_ID.getValue());
        purchaseBillDto.setApplyPurchaseBillCode(APPLY_PURCHASE_BILL_CODE.getValue());
        purchaseBillDto.setSoureBillId(APPLY_PURCHASE_BILL_ID.getValue());

        JSONObject orderResponseJson = postHttpClient(getRequestUrl(moduleName,"purchase_order"), purchaseBillDto.toString());

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
