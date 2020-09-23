package dev.banksalad.stock.web.controller;

import dev.banksalad.stock.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/test")
    public ResponseEntity test() {
        return new ResponseEntity(sampleService.getData(), HttpStatus.OK);
    }
}
