package com.exitmate.application_helper.service;

import com.exitmate.application_helper.dto.FillRequest;
import com.exitmate.application_helper.util.HwpFieldFiller;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class HwpService {

    public byte[] generateFilled(String templateName, FillRequest req) throws Exception {
        Map<String, String> values = buildValues(req);
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        try (InputStream is = resource.getInputStream()) {
            HWPFile hwp = HWPReader.fromInputStream(is);

            HwpFieldFiller.fillClickHereFields(hwp, values);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            HWPWriter.toStream(hwp, bos);
            return bos.toByteArray();
        }
    }

    private Map<String, String> buildValues(FillRequest req) {
        Map<String, String> v = new LinkedHashMap<>();

        // ── 신청자/대표자/사업 정보 ─────────────────────────────────────────────
        put(v, "companyName1", req.companyName1());                 // 상호명 (1)
        put(v, "companyName2", req.companyName2());                 // 상호명 (2)

        put(v, "applicantName1", req.applicantName1());             // 신청자 성명 (1)
        put(v, "applicantName2", req.applicantName2());             // 신청자 성명 (2)
        put(v, "applicantName3", req.applicantName3());             // 신청자 성명 (3)
        put(v, "applicantName4", req.applicantName4());             // 신청자 성명 (4)

        put(v, "businessRegistrationNumber", req.businessRegistrationNumber());  // 사업자등록번호
        put(v, "startDate", req.startDate());                       // 창업일자
        put(v, "expectedClosureDate", req.expectedClosureDate());   // 예정 폐업일
        put(v, "closureDate", req.closureDate());                   // 실제 폐업일

        put(v, "businessType", req.businessType());                 // 업태
        put(v, "businessItem", req.businessItem());                 // 업종

        put(v, "businessAddress", req.businessAddress());           // 사업장 주소
        put(v, "businessPhone", req.businessPhone());               // 사업장 전화번호
        put(v, "businessFax", req.businessFax());                   // 사업장 팩스번호

        put(v, "representativeBirthDate", req.representativeBirthDate()); // 대표자 생년월일
        put(v, "representativePhone", req.representativePhone());         // 대표자 연락처
        put(v, "representativeEmail", req.representativeEmail());         // 대표자 이메일

        if (req.regularEmployees() != null) {
            put(v, "regularEmployees", String.valueOf(req.regularEmployees())); // 상시 종업원 수
        }

        // ── 컨설팅/지원 신청 관련 ─────────────────────────────────────────────
        put(v, "consultingIssue", req.consultingIssue());           // 주요 애로 사항
        put(v, "restorationRequest", req.restorationRequest());     // 주요 신청 사항

        // ── 공사/용역 관련 ─────────────────────────────────────────────
        put(v, "contractorName", req.contractorName());             // 용역사명
        put(v, "constructionCost", req.constructionCost());         // 공사금액 (만원 단위)
        put(v, "constructionDate", req.constructionDate());         // 공사일자

        // ── 컨설턴트/계좌 정보 ─────────────────────────────────────────────
        put(v, "consultantName", req.consultantName());             // 담당 컨설턴트
        put(v, "accountHolder", req.accountHolder());               // 예금주
        put(v, "bankName", req.bankName());                         // 은행명
        put(v, "accountNumber", req.accountNumber());               // 계좌번호

        // ── 기타 ─────────────────────────────────────────────
        put(v, "note", req.note());                                 // 비고
        put(v, "name", req.name());                                 // 서명란 성명

        // ── 오늘 날짜 (자동 입력) ─────────────────────────────────────────────
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        v.put("todayMonth1", String.valueOf(today.getMonthValue()));  // 작성일 (월, 위치1)
        v.put("todayDay1", String.valueOf(today.getDayOfMonth()));    // 작성일 (일, 위치1)
        v.put("todayMonth2", String.valueOf(today.getMonthValue()));  // 작성일 (월, 위치2)
        v.put("todayDay2", String.valueOf(today.getDayOfMonth()));    // 작성일 (일, 위치2)

        // ── 체크박스 3개: 항상 체크(■) ─────────────────────────────────────────
        v.put("chkConsultingBox", "■");        // 컨설팅 신청 여부
        v.put("chkRestorationApplyBox", "■");  // 복구 지원 신청 여부
        v.put("chkRestorationGrantBox", "■");  // 지원금 신청 여부

        // ── 점포 입지 선택 (0~4, 4=기타) ─────────────────────────────────────────
        boolean[] loc = markIndex(req.storeLocationIndex(), 5);
        v.put("locApartment", box(loc[0]));    // 아파트상가
        v.put("locResidential", box(loc[1]));  // 주택가
        v.put("locDowntown", box(loc[2]));     // 번화가
        v.put("locRoadside", box(loc[3]));     // 도로변
        v.put("locOtherBox", box(loc[4]));     // 기타
        v.put("locOtherText",
                (req.storeLocationIndex() != null && req.storeLocationIndex() == 4)
                        ? nullToEmpty(req.storeLocationOtherText())   // 기타 상세 입력
                        : "");

        return v;
    }


    private static void put(Map<String, String> map, String key, String val) {
        if (key != null && val != null && !val.isBlank()) {
            map.put(key, val);
        }
    }


    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private static String box(boolean checked) {
        return checked ? "■" : "□";
    }

    private static boolean[] markIndex(Integer idx, int size) {
        boolean[] a = new boolean[size];
        if (idx != null && idx >= 0 && idx < size) {
            a[idx] = true;
        }
        return a;
    }
}
