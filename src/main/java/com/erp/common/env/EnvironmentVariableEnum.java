package com.erp.common.env;

import static com.erp.common.OperateYml.readEnvironmentVariable;

public enum EnvironmentVariableEnum {
    APPLY_PURCHASE_BILL_ID {
        @Override
        public String getValue() {
            return readEnvironmentVariable("applyPurchaseBillId");
        }
    },
    APPLY_PURCHASE_BILL_CODE {
        @Override
        public String getValue() {
            return readEnvironmentVariable("applyPurchaseBillCode");
        }
    },
    PURCHASE_ORDER_CODE {
        @Override
        public String getValue() {
            return readEnvironmentVariable("purchaseOrderCode");
        }
    },
    PURCHASE_ORDER_ID {
        @Override
        public String getValue() {
            return readEnvironmentVariable("purchaseOrderId");
        }
    },
    SUPPLIER_STOCK_IN_ID {
        @Override
        public String getValue() {
            return readEnvironmentVariable("supplierStockInId");
        }
    },
    APPLY_STOCK_UP_ID {
        @Override
        public String getValue() {
            return readEnvironmentVariable("applyStockUpId");
        }
    },
    APPLY_STOCK_UP_CODE {
        @Override
        public String getValue() {
            return readEnvironmentVariable("applyStockUpCode");
        }
    },
    INSPECTION_REPORT_ID {
        @Override
        public String getValue() {
            return readEnvironmentVariable("inspectionReportId");
        }
    },
    STOCK_IN_ORDER_ID{
        public String getValue() {
            return readEnvironmentVariable("stockInOrderId");
        }
    },
    CONTRACT_SIGNING_ID{
        public String getValue() {
            return readEnvironmentVariable("contractSigningId");
        }
    },
    CONTRACT_SIGNING_CODE{
        public String getValue() {
            return readEnvironmentVariable("contractSigningCode");
        }
    },
    STOCK_UP_BILL_ID{
        public String getValue(){
            return readEnvironmentVariable("stockUpBillId");
        }
    },
    STOCK_UP_BILL_CODE{
        public String getValue(){
            return readEnvironmentVariable("stockUpBillCode");
        }
    },
    PAYMENT_RECONCILIATION_ID{
        public String getValue(){
            return readEnvironmentVariable("paymentReconciliationId");
        }
    }
    ;
    public abstract String getValue();


}
