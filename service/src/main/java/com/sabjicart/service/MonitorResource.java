
package com.sabjicart.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorResource
{

    // health check API
    @GetMapping(value = "/ping")
    public ResponseEntity<String> register ()
    {
        return new ResponseEntity<>("Welcome to Sabji Cart service!! I am feeling Healthy.", HttpStatus.OK);
    }
}
