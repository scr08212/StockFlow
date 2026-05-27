package com.nkm.stockflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class StockPriceResponseDto {

    @JsonProperty("rt_cd")
    private String rtCd;

    @JsonProperty("msg_cd")
    private String msgCd;

    @JsonProperty("msg1")
    private String msg1;

    @JsonProperty("output")
    private PriceDetail output;


    @Getter
    @NoArgsConstructor
    public static class PriceDetail {

        @JsonProperty("stck_shrn_iscd")
        private String stockCode;

        @JsonProperty("stck_prpr")
        private String currentPrice;

        @JsonProperty("prdy_vrss")
        private String prdyVrss; // 전일 대비 금액

        @JsonProperty("prdy_ctrt")
        private String prdyCtrt; // 전일 대비 등락률
    }
}
