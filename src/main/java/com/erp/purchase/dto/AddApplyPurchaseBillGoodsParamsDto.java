package com.erp.purchase.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AddApplyPurchaseBillGoodsParamsDto {

    private String applyPurchaseBillId;
    private ArrayList<String> purchaseBillDetails;

}
