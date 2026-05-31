package com.nkm.stockflow;

import com.nkm.stockflow.dto.StockDailyPriceResponseDto;
import com.nkm.stockflow.dto.StockPriceResponseDto;
import com.nkm.stockflow.service.KoreaInvestmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
            StockPriceResponseDto priceData = service.fetchCurrentPrice(accessToken, "005930");
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

            System.out.println("3. 삼성전자(005930) 과거 주가 조회 시도");
            StockDailyPriceResponseDto priceChartData = service.fetchDailyPrices(accessToken, "005930");
            if(priceChartData != null && "0".equals(priceChartData.getRtCd())) {
                List<StockDailyPriceResponseDto.DailyPriceDetail> output2 = priceChartData.getOutput2();
                System.out.println("[조회 성공]");
                for(StockDailyPriceResponseDto.DailyPriceDetail priceDetail : output2) {
                    System.out.println("### 영업 일자: " + priceDetail.getStckBsopDate());
                    System.out.println("주식 종가: " + priceDetail.getStckClpr());
                    System.out.println("주식 시가: " + priceDetail.getStckOprc());
                    System.out.println("주식 최고가: " + priceDetail.getStckHgpr());
                    System.out.println("주식 최저가: " + priceDetail.getStckLwpr());
                    System.out.println("주식 거래량: " + priceDetail.getAcmlVol());
                    System.out.println("전일 대비: " + priceDetail.getPrdyVrss());
                }
            } else {
                System.out.println("[조회 실패] : " + (priceChartData != null ? priceChartData.getMsg1() : "무응답"));
            }
            System.out.println("==========================");
        };
    }
}
