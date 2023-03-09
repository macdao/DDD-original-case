package com.ddd.original.interestcalculator.v0.domain;

import java.util.List;

public interface AssetRepository {
    List<Asset> findAll();

    void sendPaymentService(Asset asset);

    Asset findById(Long assetId);

    InterestPaymentHistory save(InterestPaymentHistory interestPaymentHistory);

    FeePaymentHistory save(FeePaymentHistory feePaymentHistory);
}
