package com.erp.purchase;

import com.alibaba.fastjson2.JSONObject;
import com.erp.common.DateHandle;
import com.erp.purchase.dto.CreateApplyPurchaseBillParamsDto;

import static com.erp.common.DateHandle.getTargetDate;
import static com.erp.common.HttpClientCommon.getRequestUrl;
import static com.erp.common.HttpClientCommon.postHttpClient;
import static com.erp.common.OperateYml.writerEnvironmentVariable;


public class CreateApplyPurchaseBill extends ApplyPurchaseBillDto {

    private static String requestUrl;
    private static JSONObject responseJson;
    private static final String moduleName = "purchase";

    // 创建常规申购单
    public static void applyPurchaseBill() {

        CreateApplyPurchaseBillParamsDto createApplyPurchaseBillParamsDto = new CreateApplyPurchaseBillParamsDto();
        createApplyPurchaseBillParamsDto.setExpectedArrivalTime(getTargetDate(DateHandle.getDate(5), "T"));
        createApplyPurchaseBillParamsDto.setExpectedPutOnSaleTime(getTargetDate(DateHandle.getMonth(4), "T"));

        responseJson = postHttpClient(getRequestUrl(moduleName,"apply_purchase_bill"), createApplyPurchaseBillParamsDto);

        writerEnvironmentVariable("applyPurchaseBillId", JSONObject.parseObject(responseJson.getString("result"))
                .getString("id"));

    }

    // 常规申购单物料明细维护
    public static void applyPurchaseBillDetails(){
        applyPurchaseBillDetailsParams = applyPurchaseBillDetailsParams.replace("6834", APPLY_PURCHASE_BILL_ID.getValue());
        responseJson = postHttpClient(getRequestUrl(moduleName,"apply_purchase_bill_detail"), applyPurchaseBillDetailsParams);

    }
    // 申购单提交审核
    public static void applyPurchaseBillReview(){
        requestUrl = getRequestUrl(moduleName,"apply_purchase_bill_review")
                .replace("6834", APPLY_PURCHASE_BILL_ID.getValue());
        putHttpClient(requestUrl, "");
    }
    // 查询申购单ApplyPurchaseBillCode
    public static void applyPurchaseBillCode(){
        requestUrl = getRequestUrl(moduleName,"apply_purchase_bill_code")
                .replace("6834", APPLY_PURCHASE_BILL_ID.getValue());
        responseJson = JSONObject.parseObject(getHttpClient(requestUrl).getString("result"));

        writerEnvironmentVariable("applyPurchaseBillCode", responseJson.getString("applyPurchaseBillCode"));
        /**
        int approveStatus = responseJson.getIntValue("approveStatus");
        int applyPurchaseBillStatus = responseJson.getIntValue("applyPurchaseBillStatus");
        if (approveStatus == 2 || applyPurchaseBillStatus == 1) {
            String selectSentence = String.format("update `wecharmer.purchase`.applypurchasebill set approveStatus = 3 , applyPurchaseBillStatus = 2 where id = %s" , applyPurchaseBillDto.getApplyPurchaseBillId());
            executeSql(selectSentence);
        }
        */
    }

    public static void createApplyPurchaseBill() {
        try{
            applyPurchaseBill();
            Thread.sleep(1000);
            applyPurchaseBillDetails();
            Thread.sleep(1000);
            applyPurchaseBillReview();
            Thread.sleep(1000);
            applyPurchaseBillCode();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
