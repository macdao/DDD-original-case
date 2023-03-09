package com.ddd.original.interestcalculator.v0.adpter.driving;

import com.ddd.original.interestcalculator.v0.domain.Asset;
import com.ddd.original.interestcalculator.v0.domain.AssetRepository;
import com.ddd.original.interestcalculator.v0.domain.FeePaymentHistory;
import com.ddd.original.interestcalculator.v0.domain.InterestPaymentHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssetRepositoryImpl implements AssetRepository {
    @Override
    public List<Asset> findAll() {
        return null;
    }

    @Override
    public void sendPaymentService(Asset asset) {

    }

    @Override
    public Asset findById(Long assetId) {
        return null;
    }

    @Override
    public InterestPaymentHistory save(InterestPaymentHistory interestPaymentHistory) {
        return null;
    }

    @Override
    public FeePaymentHistory save(FeePaymentHistory feePaymentHistory) {
        return null;
    }
}
