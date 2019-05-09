package com.demo.exchanger.client;

import com.demo.exchanger.converter.DateConverter;
import com.demo.exchanger.dto.FixerResponseDto;
import com.demo.exchanger.exception.FixerClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FixerClient {

    @Value("${fixer.api.url}")
    private String URL;
    @Value("${fixer.api.key}")
    private String ACCESS_KEY;

    private final static String RUB_CURRENCY_NAME = "RUB";
    private final RestTemplate restTemplate;

    @Autowired
    public FixerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getHistoricalRubExchangeRate(Date historicalDate) throws FixerClientException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + DateConverter.getFixerRequestDate(historicalDate))
                .queryParam("access_key", ACCESS_KEY)
                .queryParam("symbols", RUB_CURRENCY_NAME);
        FixerResponseDto fixerResponseDto = restTemplate.getForObject(builder.toUriString(), FixerResponseDto.class);

        if (fixerResponseDto == null) {
            log.error("Request for fixer.io returned null for date {}", historicalDate);
            throw new FixerClientException("Request for fixer.io returned null");
        }
        if (!fixerResponseDto.isSuccess()) {
            String errorMessage = fixerResponseDto.getError().entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("; "));
            throw new FixerClientException("Fixer api error (" + errorMessage + ")");
        }
        if (CollectionUtils.isEmpty(fixerResponseDto.getRates())) {
            log.error("Returned rates is empty for date {}", historicalDate);
            throw new FixerClientException("Returned rates is empty");
        }

        return fixerResponseDto.getRates().get(RUB_CURRENCY_NAME);
    }

    public BigDecimal getTodayRubExchangeRate() throws FixerClientException {
        return getHistoricalRubExchangeRate(new Date());
    }


}
