package com.nkm.stockflow;

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
            System.out.println("한투 토큰 가져오기 시도");
            String accessToken = service.getAccessToken();
            System.out.println("발급된 토큰: " + accessToken);
            System.out.println("==========================");
        };
    }
}
