package com.exitmate.application_helper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FillRequest(
        // ── 신청자/대표자/사업 정보 ─────────────────────────────────────────────
        @JsonProperty("companyName1")
        @Size(max = 100)
        String companyName1,

        @JsonProperty("companyName2")
        @Size(max = 100)
        String companyName2,

        @JsonProperty("applicantName1")
        @Size(max = 100)
        String applicantName1,

        @JsonProperty("applicantName2")
        @Size(max = 100)
        String applicantName2,

        @JsonProperty("applicantName3")
        @Size(max = 100)
        String applicantName3,

        @JsonProperty("applicantName4")
        @Size(max = 100)
        String applicantName4,

        @JsonProperty("businessRegistrationNumber")
        @Pattern(regexp = "^[0-9\\-]{10,12}$", message = "사업자등록번호 형식이 올바르지 않습니다.")
        String businessRegistrationNumber,

        @JsonProperty("startDate")
        @Size(max = 20)
        String startDate,            // "YYYY-MM-DD"

        @JsonProperty("expectedClosureDate")
        @Size(max = 20)
        String expectedClosureDate,  // "YYYY-MM-DD"

        @JsonProperty("closureDate")
        @Size(max = 20)
        String closureDate,         // 폐업일자

        @JsonProperty("businessType")
        @Size(max = 50)
        String businessType,         // 업태

        @JsonProperty("businessItem")
        @Size(max = 50)
        String businessItem,         // 업종

        // ── 연락/주소 ────────────────────────────────────────────────────────────
        @JsonProperty("businessAddress")
        @Size(max = 200)
        String businessAddress,

        @JsonProperty("businessPhone")
        @Size(max = 30)
        String businessPhone,

        @JsonProperty("businessFax")
        @Size(max = 30)
        String businessFax,

        // ── 대표자 상세 ──────────────────────────────────────────────────────────
        @JsonProperty("representativeBirthDate")
        @Size(max = 20)
        String representativeBirthDate, // "YYYY-MM-DD"

        @JsonProperty("representativePhone")
        @Size(max = 30)
        String representativePhone,

        @JsonProperty("representativeEmail")
        @Email
        @Size(max = 100) String representativeEmail,

        // ── 운영현황 ─────────────────────────────────────────────────────────────
        @JsonProperty("regularEmployees")
        @Min(0)
        @Max(100000)
        Integer regularEmployees,

        // ── 신청 내용(컨설팅/원상복구) ───────────────────────────────────────────
        @JsonProperty("consultingIssue")
        @Size(max = 2000)
        String consultingIssue,      // 주요 애로 사항

        @JsonProperty("restorationRequest")
        @Size(max = 2000)
        String restorationRequest,   // 주요 신청 사항

        // ── 계좌 정보 ────────────────────────────────────────────────────────────
        @JsonProperty("accountHolder")
        @Size(max = 100)
        String accountHolder,

        @JsonProperty("bankName")
        @Size(max = 100)
        String bankName,

        @JsonProperty("accountNumber")
        @Size(max = 50)
        String accountNumber,

        @JsonProperty("note")
        @Size(max = 500)
        String note,

        // ── 공사/용역 ────────────────────────────────────────────────────────────
        @JsonProperty("contractorName")
        @Size(max = 100)
        String contractorName, // 용역사명

        @JsonProperty("constructionCost")
        @Size(max = 50)
        String constructionCost, // 공사금액 (만원 단위)

        @JsonProperty("constructionDate")
        @Size(max = 50)
        String constructionDate, // "YYYY.MM.DD ~ YYYY.MM.DD"

        // ── 기타 ────────────────────────────────────────────────────────────────
        @JsonProperty("consultantName")
        @Size(max = 100)
        String consultantName,

        @JsonProperty("name")
        @Size(max = 100)
        String name, // 서명란 성명 등

        // 점포입지: 0=아파트상가, 1=주택가, 2=번화가, 3=도로변, 4=기타
        @JsonProperty("storeLocationIndex")
        @Min(0)
        @Max(4)
        Integer storeLocationIndex,

        @JsonProperty("storeLocationOtherText")
        @Size(max = 50)
        String storeLocationOtherText
) {
}
