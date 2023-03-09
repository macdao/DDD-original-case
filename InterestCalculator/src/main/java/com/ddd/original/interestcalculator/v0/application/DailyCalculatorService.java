package com.ddd.original.interestcalculator.v0.application;

import com.ddd.original.interestcalculator.v0.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCalculatorService {

    private final AssetRepository assetRepository;

    public void calculate() {
        List<Asset> allAsset = assetRepository.findAll();
        allAsset.forEach(this::calculate);
    }

    public void calculate(Asset asset) {
        BigDecimal interest = null;
        if (asset.getInterestType() == InterestType.Simple) {
            //some logic of simple interest

        } else if (asset.getInterestType() == InterestType.Compound) {
            //some logic of compound interest
        } else if (asset.getInterestType() == InterestType.Fixed) {
            //some logic of fixed interest
        }
        asset.setInterestDueAmount(asset.getInterestDueAmount().add(interest));

        if (LocalDate.now().equals(asset.getDayOfMouthDue())) {
            //some logic of fee
            BigDecimal fee = null;
            asset.setFeeDueAmount(asset.getFeeDueAmount().add(fee));
        }
        //....
        assetRepository.sendPaymentService(asset);
    }

    public void updatePaymentInfo(PaymentResultParams paymentResult) {

        Asset asset = assetRepository.findById(paymentResult.getAssetId());
        if (asset == null) {
            return;
        }
        if (paymentResult.getPaymentType() == PaymentType.Interest) {
            InterestPaymentHistory interestPayment = InterestPaymentHistory.builder().assetId(asset.getId())
                    .paymentDate(paymentResult.getPaymentDate())
                    .paymentAmount(paymentResult.getAmount())
                    .build();
            assetRepository.save(interestPayment);
        } else if (paymentResult.getPaymentType() == PaymentType.Fee) {
            FeePaymentHistory feePayment = FeePaymentHistory.builder().assetId(asset.getId())
                    .paymentDate(paymentResult.getPaymentDate())
                    .paymentAmount(paymentResult.getAmount())
                    .build();
            assetRepository.save(feePayment);
        }
    }

}
