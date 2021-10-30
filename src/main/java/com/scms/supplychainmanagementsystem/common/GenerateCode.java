package com.scms.supplychainmanagementsystem.common;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GenerateCode {
    public String genCodeByDate(String prefix, Long id) {
        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        return prefix + String.valueOf(year).substring(2) + month + day + 1000 + (id < 1000 ? id : id % 1000);
    }
}
