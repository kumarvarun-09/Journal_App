package com.cc.journalApp.service;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IWeatherService {
    Optional<?> getWeatherData(String city);
}
