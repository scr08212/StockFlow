package com.nkm.stockflow;

import com.nkm.stockflow.dto.StockPriceResponseDto;
import com.nkm.stockflow.service.KoreaInvestmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockflowApplication.class, args);
	}

    @Bean
    public CommandLineRunner testRunner(KoreaInvestmentService service) {
        return args -> {
            System.out.println("==========================");
            System.out.println("1. 한투 토큰 가져오기 시도");
            String accessToken = service.getAccessToken();
            System.out.println("발급된 토큰: " + accessToken);

            System.out.println("2. 삼성전자(005930) 현재가 조회 시도");
            StockPriceResponseDto priceData = service.getStockCurrentPrice(accessToken, "005930");
            if(priceData != null && "0".equals(priceData.getRtCd())) {
                StockPriceResponseDto.PriceDetail priceDetail = priceData.getOutput();
                System.out.println("[조회 성공]");
                System.out.println("종목 코드: " + priceDetail.getStockCode());
                System.out.println("현재가격: " + priceDetail.getCurrentPrice());
                System.out.println("전일대비: " + priceDetail.getPrdyVrss());
                System.out.println("등락률: " + priceDetail.getPrdyCtrt());
            } else {
                System.out.println("[조회 실패] : " + (priceData != null ? priceData.getMsg1() : "무응답"));
            }
            System.out.println("==========================");
        };
    }
}
