package com.tutorial.websocketdemo.form;

import com.tutorial.websocketdemo.enums.SocketType;
import lombok.Data;

@Data
public class CryptoPriceRequest {

    private String ticker;
    private SocketType type;
    private String period;

}
