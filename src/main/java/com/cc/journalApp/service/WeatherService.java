package com.cc.journalApp.service;

import com.cc.journalApp.constants.ApiConstants;
import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.Resources;
import com.cc.journalApp.models.WeatherData;
import com.cc.journalApp.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {
    private final ResourceRepository resourceRepository;
    private final WebClient.Builder webClientBuilder;

    public Optional<?> getWeatherData(String city) {
        Resources record = resourceRepository.getValueByKey(ApiConstants.WEATHER_API_BASEURL)
                .orElse(null);
        if (record == null) {
            throw new ResourceNotFoundException(ApiConstants.WEATHER_API_BASEURL);
        }
        final String WEATHER_API_BASEURL = record.getValue();
        record = resourceRepository.getValueByKey(ApiConstants.WEATHER_API_KEY)
                .orElse(null);
        if (record == null) {
            throw new ResourceNotFoundException(ApiConstants.WEATHER_API_KEY);
        }
        final String WEATHER_API_KEY = record.getValue();

        WebClient webClient = webClientBuilder.baseUrl(WEATHER_API_BASEURL).build();
        WeatherData weatherData = webClient.get().uri(
                        uriBuilder -> {
                            uriBuilder.queryParam(ApiConstants.QUERY_PARAM_CITY, city);
                            uriBuilder.queryParam(ApiConstants.QUERY_PARAM_APP_ID, WEATHER_API_KEY);
                            return uriBuilder.build();
                        })
                .retrieve()
                .bodyToMono(WeatherData.class)
                .block();
        if (weatherData == null) {
            throw new ResourceNotFoundException(city);
        }
        log.info("Weather data found for city: {}, : {}", city, weatherData.toString());
        return Optional.of(weatherData);
    }
}
