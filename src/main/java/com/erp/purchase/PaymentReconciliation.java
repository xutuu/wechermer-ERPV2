package com.erp.purchase;

import static com.wechermer.common.HttpClientExample.*;
import static com.wechermer.common.OperateYml.writerEnvironmentVariable;
import static com.wechermer.dto.PaymentReconciliationDto.createPaymentReconciliationParams;
import static com.wechermer.enumeration.EnvironmentVariableEnum.PAYMENT_RECONCILIATION_ID;
import static com.wechermer.enumeration.EnvironmentVariableEnum.PURCHASE_ORDER_ID;

public class PaymentReconciliation {

    private static final String oldPurchaseId = "6468";
    private static final String module = "purchase";

    // 创建货款对账单
    public static void createPaymentReconciliation() {

        createPaymentReconciliationParams = createPaymentReconciliationParams.replace(oldPurchaseId, PURCHASE_ORDER_ID.getValue());

        String paymentReconciliationId = postHttpClient(getRequestUrl(module, "create_payment_statement"), createPaymentReconciliationParams)
                .getString("result");

        writerEnvironmentVariable("paymentReconciliationId", paymentReconciliationId);
    }

    // 货款对账提交审核
    public static void submitPaymentStatement(){
        putHttpClient(getRequestUrl(module, "submit_payment_statement").replace("395", PAYMENT_RECONCILIATION_ID.getValue()));
    }

    public static void  paymentReconciliation(){
        try {
            createPaymentReconciliation();
            Thread.sleep(1000);

            submitPaymentStatement();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
