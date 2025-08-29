package com.exitmate.application_helper.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import kr.dogfoot.hwplib.object.HWPFile;
import java.util.Map;
import kr.dogfoot.hwplib.tool.objectfinder.FieldFinder;
import kr.dogfoot.hwplib.tool.objectfinder.SetFieldResult;


public class HwpFieldFiller {

    public static void fillClickHereFields(HWPFile hwp, Map<String, String> values) {
        values.forEach((fieldName, newText) -> {
            ArrayList<String> textList = new ArrayList<>();
            textList.add(newText);

            SetFieldResult result = null;
            try {
                result = FieldFinder.setClickHereText(hwp, fieldName, textList);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

//            System.out.printf("필드 '%s' 설정 결과 = %s\n", fieldName, result);
        });
    }

}
