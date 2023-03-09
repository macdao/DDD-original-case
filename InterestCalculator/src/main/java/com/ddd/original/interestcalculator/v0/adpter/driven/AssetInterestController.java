package com.ddd.original.interestcalculator.v0.adpter.driven;

import com.ddd.original.interestcalculator.v0.application.DailyCalculatorService;
import com.ddd.original.interestcalculator.v0.application.PaymentResultParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssetInterestController {

    private final DailyCalculatorService dailyCalculatorService;

    @PostMapping("/calculate")
    public void calculate() {
        dailyCalculatorService.calculate();
    }

    @PostMapping("/calculate/payment/{assetId}")
    public void updatePayment(@RequestBody PaymentResultParams paymentResultParams) {
        dailyCalculatorService.updatePaymentInfo(paymentResultParams);
    }
}
