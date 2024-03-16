package com.tutorial.websocketdemo.form;

import java.util.List;
import com.tutorial.websocketdemo.enums.SocketMethod;

import lombok.Data;

@Data
public class BinanceRequest {
    
    private SocketMethod method;
    private List<String> params;
    private Long id;

}
