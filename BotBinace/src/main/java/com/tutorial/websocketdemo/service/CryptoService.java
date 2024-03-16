package com.tutorial.websocketdemo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.websocketdemo.converter.PriceDtoConverter;
import com.tutorial.websocketdemo.data.BinanceKlineData;
import com.tutorial.websocketdemo.dto.PriceDto;
import com.tutorial.websocketdemo.enums.Period;
import com.tutorial.websocketdemo.form.CryptoPriceRequest;

@Service
public class CryptoService {

    private final PriceDtoConverter priceDtoConverter;

    private final Map<String, BinanceKlineData> storage = new LinkedHashMap<>();

    public CryptoService(PriceDtoConverter priceDtoConverter) {
        this.priceDtoConverter = priceDtoConverter;
    }

    public void add(BinanceKlineData data) {
        if (data.getStream() != null) {
            storage.put(data.getStream(), data);
        }
    }

    public List<PriceDto> getList(List<CryptoPriceRequest> requestList) {
        List<String> streams = requestList.stream().map(
                (c) -> c.getTicker().toLowerCase() + "@" + Period.getPeriodByTypeAndPeriod(c.getType(), c.getPeriod()))
                .collect(Collectors.toList());

        return streams.stream().map((c) -> {
            return priceDtoConverter.convert(storage.get(c));
        }).filter((c) -> c != null).collect(Collectors.toList());
    }

}
