package com.nkm.stockflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// https://apiportal.koreainvestment.com/apiservice-apiservice?/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice

@Getter
@AllArgsConstructor
public class StockDailyPriceResponseDto {

    @JsonProperty("rt_cd")
    private String rtCd;

    @JsonProperty("msg_cd")
    private String msgCd;

    @JsonProperty("msg1")
    private String msg1;

    @JsonProperty("output2")
    private List<DailyPriceDetail> output2;

    @Getter
    @NoArgsConstructor
    public static class DailyPriceDetail {

        @JsonProperty("stck_bsop_date")
        private String stckBsopDate; // 주식 영업 일자

        @JsonProperty("stck_clpr")
        private String stckClpr; // 주식 종가

        @JsonProperty("stck_oprc")
        private String stckOprc; // 주식 시가2

        @JsonProperty("stck_hgpr")
        private String stckHgpr; // 주식 최고가

        @JsonProperty("stck_lwpr")
        private String stckLwpr; // 주식 최저가

        @JsonProperty("acml_vol")
        private String acmlVol; // 누적 거래량

        @JsonProperty("acml_tr_pbmn")
        private String acmlTrPbmn; // 누적 거래 대금

        @JsonProperty("flng_cls_code")
        private String flngClsCode; // 락 구분 코드

        @JsonProperty("prtt_rate")
        private String prttRate; // 분할 비율

        @JsonProperty("mod_yn")
        private String modyn; // 변경 여부

        @JsonProperty("prdy_vrss_sign")
        private String prdyVrsSign; // 전일 대비 부호

        @JsonProperty("prdy_vrss")
        private String prdyVrss; // 전일 대비

        @JsonProperty("revl_issu_reas")
        private String revlIssuReas; // 재평가사유코드
    }
}
