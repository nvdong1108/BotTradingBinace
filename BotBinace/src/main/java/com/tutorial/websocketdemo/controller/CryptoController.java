package com.tutorial.websocketdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.websocketdemo.dto.Response;
import com.tutorial.websocketdemo.form.CryptoPriceRequest;
import com.tutorial.websocketdemo.service.CryptoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/realtime")
    public Response<?> getPriceByTickerAndPeriod(@RequestBody List<CryptoPriceRequest> requestList) {
        var data = cryptoService.getList(requestList);
        return Response.build(HttpStatus.OK, data, data.size());
    }

}