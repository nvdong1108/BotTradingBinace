package com.tutorial.websocketdemo.converter;

import org.springframework.stereotype.Component;

import com.tutorial.websocketdemo.data.BinanceKlineData;
import com.tutorial.websocketdemo.dto.PriceDto;
import com.tutorial.websocketdemo.enums.SocketType;

@Component
public class PriceDtoConverter {

    public PriceDto convert(BinanceKlineData data) {
        if (data == null) {
            return null;
        }

        PriceDto dto = new PriceDto();

        if (data.getStream() != null) {
            dto.setTicker(data.getData().getSymbol());
            dto.setType(SocketType.CRYPTO);
            dto.setPeriod(data.getData().getKline().getInterval());
            dto.setTime(data.getData().getEventTime());
            dto.setStartTime(data.getData().getKline().getStartTime());
            dto.setEndTime(data.getData().getKline().getEndTime());
            dto.setOpen(Double.valueOf(data.getData().getKline().getOpenPrice()));
            dto.setClose(Double.valueOf(data.getData().getKline().getClosePrice()));
            dto.setHigh(Double.valueOf(data.getData().getKline().getHighPrice()));
            dto.setLow(Double.valueOf(data.getData().getKline().getLowPrice()));
            dto.setVolume(Double.valueOf(data.getData().getKline().getVolume()));
            dto.setNetChange(dto.getClose() - dto.getOpen());
            dto.setPctChange(dto.getNetChange() / dto.getOpen() * 100);
        }

        return dto;
    }

}
