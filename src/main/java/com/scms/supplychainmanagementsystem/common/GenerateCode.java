package com.scms.supplychainmanagementsystem.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class GenerateCode {
    public String genCodeByDate(String prefix) {
        Instant now = Instant.now();

        return null;
    }
}
