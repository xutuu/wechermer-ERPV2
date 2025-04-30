package com.erp.purchase;

import com.alibaba.fastjson2.JSONObject;


public class ContractManage {

    private static final String module = "purchase";
    private static final String oldContractSigningId = "380";

    // 创建采购单合同
    public static void createPurchaseContractSigning(){
        createPurchaseContractSigningParams = createPurchaseContractSigningParams.replace("5949",PURCHASE_ORDER_ID.getValue());
        JSONObject resultJson = postHttpClient(getRequestUrl(module, "agreement_baseInfo"), createPurchaseContractSigningParams).getJSONObject("result");
        writerEnvironmentVariable("contractSigningId", resultJson.getString("id"));
        writerEnvironmentVariable("contractSigningCode", resultJson.getString("agreementCode"));

    }

    // 生成采购合同文件并保存
    public static void createPurchaseContractDoc(){
        String purchaseContractDoc = getHttpClient(getRequestUrl(module, "create_agreement_doc").replace(oldContractSigningId, CONTRACT_SIGNING_ID.getValue())).getString("result");
        auditContractSignInfoParams = auditContractSignInfoParams.replace("HT25040300001_25040300251.pdf",purchaseContractDoc).replace(oldContractSigningId, CONTRACT_SIGNING_ID.getValue());
        putHttpClient(getRequestUrl(module, "audit_agreement_doc"),auditContractSignInfoParams);
    }

    // 发起采购合同签署
    public static void initiatePurchaseContractSign(){
        initiatePurchaseContractSignParams = initiatePurchaseContractSignParams.replace(oldContractSigningId,CONTRACT_SIGNING_ID.getValue());
        putHttpClient(getRequestUrl(module, "initiate_agreement"), initiatePurchaseContractSignParams);
    }

    // 确认收到采购合同
    public static void receivePurchaseContract(){
        postHttpClient(getRequestUrl(module, "received_agreement").replace(oldContractSigningId, CONTRACT_SIGNING_ID.getValue()),"");
    }

    // 签署采购合同
    public static void signContract(){
        signContractParams = signContractParams.replace(oldContractSigningId,CONTRACT_SIGNING_ID.getValue());
        putHttpClient(getRequestUrl(module, "sign_agreement"),signContractParams);
    }

    // 采购单合同签署
    public static void createPurchaseSignContract(){
        try{

            Thread.sleep(1000);

            createPurchaseContractSigning();

            Thread.sleep(1000);

            createPurchaseContractDoc();

            Thread.sleep(1000);

            initiatePurchaseContractSign();

            Thread.sleep(1000);

            receivePurchaseContract();

            Thread.sleep(1000);

            signContract();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
