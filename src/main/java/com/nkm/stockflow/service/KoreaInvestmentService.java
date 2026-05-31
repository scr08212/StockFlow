package com.nkm.stockflow.service;

import com.nkm.stockflow.dto.StockDailyPriceResponseDto;
import com.nkm.stockflow.dto.StockPriceResponseDto;
import com.nkm.stockflow.dto.TokenRequestDto;
import com.nkm.stockflow.dto.TokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class KoreaInvestmentService {

    private final WebClient webClient;

    @Value("${koreainvestment.api.key}")
    private String appkey;

    @Value("${koreainvestment.api.secret}")
    private String appSecret;

    public KoreaInvestmentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAccessToken() {
        TokenRequestDto requestBody = new TokenRequestDto(appkey, appSecret);

        TokenResponseDto response = webClient.post()
                .uri("/oauth2/tokenP")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(TokenResponseDto.class)
                .block();

        if(response != null){
            return response.getAccessToken();
        }
        throw new RuntimeException("한투 API 토큰 발급에 실패했습니다.");
    }

    public StockPriceResponseDto fetchCurrentPrice(String token, String stockCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-price")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .build())
                .header("authorization", "Bearer " + token)
                .header("appkey", appkey)
                .header("appsecret", appSecret)
                .header("tr_id", "FHKST01010100")
                .header("custtype", "P")
                .retrieve()
                .bodyToMono(StockPriceResponseDto.class)
                .block();
    }

    public StockDailyPriceResponseDto fetchDailyPrices(String token, String stockCode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(130);

        String fidInputDate1 = startDate.format(dtf);
        String fidInputDate2 = endDate.format(dtf);


        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .queryParam("FID_INPUT_DATE_1", fidInputDate1)
                        .queryParam("FID_INPUT_DATE_2", fidInputDate2)
                        .queryParam("FID_PERIOD_DIV_CODE", "D")
                        .queryParam("FID_ORG_ADJ_PRC", "0")
                        .build())
                .header("authorization", "Bearer " + token)
                .header("appkey", appkey)
                .header("appsecret", appSecret)
                .header("tr_id", "FHKST03010100")
                .header("custtype", "P")
                .retrieve()
                .bodyToMono(StockDailyPriceResponseDto.class)
                .block();
    }
}
