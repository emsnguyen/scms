package com.scms.supplychainmanagementsystem.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class GenerateCode {
    public String genCodeByDate(String prefix) {
        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        return prefix + String.valueOf(year).substring(2) + month + day + 1000;
    }
}
