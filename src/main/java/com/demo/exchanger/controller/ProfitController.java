package com.demo.exchanger.controller;

import com.demo.exchanger.http.ResponseModel;
import com.demo.exchanger.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
public class ProfitController {

    private final ProfitService profitService;

    @Autowired
    public ProfitController(ProfitService profitService) {
        this.profitService = profitService;
    }

    @GetMapping("/api/v1/profit")
    public ResponseEntity getProfitBy(@RequestParam BigDecimal amount,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date historicalDate) {
        BigDecimal profit = profitService.calculate(historicalDate, amount);
        return ResponseEntity.ok(ResponseModel.ok(profit));
    }
}
