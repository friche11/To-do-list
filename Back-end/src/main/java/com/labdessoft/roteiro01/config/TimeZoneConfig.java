package com.web.labdessoft.config;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

@Component
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}