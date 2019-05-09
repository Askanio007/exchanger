package com.demo.exchanger.service;

import java.math.BigDecimal;
import java.util.Date;

public interface ProfitService {
    BigDecimal calculate(Date historicalDate, BigDecimal amount);
}
