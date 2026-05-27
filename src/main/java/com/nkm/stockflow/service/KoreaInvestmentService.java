package com.nkm.stockflow.service;

import com.nkm.stockflow.dto.TokenRequestDto;
import com.nkm.stockflow.dto.TokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KoreaInvestmentService {

    private final WebClient webClient;

    @Value("${koreainvestment.api.key}")
    private String apiKey;

    @Value("${koreainvestment.api.secret}")
    private String apiSecret;

    public KoreaInvestmentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAccessToken() {
        TokenRequestDto requestBody = new TokenRequestDto(apiKey, apiSecret);

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
}
