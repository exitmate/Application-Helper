package com.exitmate.application_helper.controller;


import com.exitmate.application_helper.dto.FillRequest;
import com.exitmate.application_helper.service.HwpService;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hwp")
@RequiredArgsConstructor
public class HwpController {

    private final HwpService hwpService;

    @PostMapping("/fill")
    public ResponseEntity<byte[]> fill(@Valid @RequestBody FillRequest req) throws Exception {
        byte[] bytes = hwpService.generateFilled("신청서류.hwp", req);
        String filename = URLEncoder.encode("신청서류(작성본).hwp", StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }


}
