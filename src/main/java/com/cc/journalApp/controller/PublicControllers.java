package com.cc.journalApp.controller;

import com.cc.journalApp.dto.UserDTO;
import com.cc.journalApp.exceptions.UserNameAlreadyInUseException;
import com.cc.journalApp.models.WeatherData;
import com.cc.journalApp.request.UserRequest;
import com.cc.journalApp.service.IUserService;
import com.cc.journalApp.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicControllers {
    private final IUserService userService;
    private final IWeatherService weatherService;

    @PostMapping("/createUser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest request) {
        try {
            UserDTO user = new UserDTO(userService.createUser(request));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UserNameAlreadyInUseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/weather-data")
    public ResponseEntity<?> getWeatherData(@RequestParam String city) {
        Optional<?> weatherData = weatherService.getWeatherData(city);
        return ResponseEntity.ok(weatherData);
    }
}
