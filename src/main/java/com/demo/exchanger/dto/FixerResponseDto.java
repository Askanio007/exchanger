package com.demo.exchanger.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

@Data
public class FixerResponseDto {
    private boolean success;
    private boolean historical;
    private String date;
    private String base;
    private LinkedHashMap<String, BigDecimal> rates;
    private LinkedHashMap<String, String> error;
}
