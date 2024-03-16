package com.tutorial.websocketdemo.dto;

import java.io.Serializable;

import com.tutorial.websocketdemo.enums.SocketType;

import lombok.Data;

@Data
public class PriceDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ticker;
    private SocketType type;
    private String period;
    private Long time;
    private Long startTime;
    private Long endTime;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double volume;
    private Double netChange;
    private Double pctChange;

}
