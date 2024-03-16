package com.tutorial.websocketdemo.form;

import java.util.List;

import com.tutorial.websocketdemo.enums.SocketMethod;
import com.tutorial.websocketdemo.enums.SocketType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocketRequest {

    private SocketMethod method;
    private SocketType type;
    private List<String> tickers;
    private String period;

}
