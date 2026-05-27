package com.nkm.stockflow.service;

import com.nkm.stockflow.dto.StockPriceResponseDto;
import com.nkm.stockflow.dto.TokenRequestDto;
import com.nkm.stockflow.dto.TokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public StockPriceResponseDto getStockCurrentPrice(String token, String stockCode) {
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
}
