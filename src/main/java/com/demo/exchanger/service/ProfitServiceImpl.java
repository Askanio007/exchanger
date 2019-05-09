package com.demo.exchanger.service;

import com.demo.exchanger.client.FixerClient;
import com.demo.exchanger.util.ComparisonDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ProfitServiceImpl implements ProfitService {

    private static final BigDecimal SPREAD = new BigDecimal(0.005);
    private final FixerClient fixerClient;

    @Autowired
    public ProfitServiceImpl(FixerClient fixerClient) {
        this.fixerClient = fixerClient;
    }

    @Override
    public BigDecimal calculate(Date historicalDate, BigDecimal amount) {
        validate(historicalDate, amount);

        BigDecimal histRubExchangeRate = fixerClient.getHistoricalRubExchangeRate(historicalDate);
        BigDecimal todayRubExchangeRate = fixerClient.getTodayRubExchangeRate();

        BigDecimal historicalProfit = amount.multiply(calculateRateWithSpread(histRubExchangeRate));
        BigDecimal todayProfit = amount.multiply(calculateRateWithSpread(todayRubExchangeRate));
        return todayProfit.subtract(historicalProfit);
    }

    private void validate(Date historicalDate, BigDecimal amount) {
        if (amount == null || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new IllegalArgumentException("Amount cannot be less than or equal to zero.");
        }
        if (ComparisonDateUtils.isAfterToday(historicalDate)) {
            throw new IllegalArgumentException("Historical date cannot be greater than current");
        }
    }

    private BigDecimal calculateRateWithSpread(BigDecimal rate) {
        return rate.subtract(rate.multiply(SPREAD));
    }
}
