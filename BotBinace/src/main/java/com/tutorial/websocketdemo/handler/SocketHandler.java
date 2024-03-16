package com.tutorial.websocketdemo.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.websocketdemo.client.BinanceClient;
import com.tutorial.websocketdemo.enums.Period;
import com.tutorial.websocketdemo.enums.SocketType;
import com.tutorial.websocketdemo.form.BinanceRequest;
import com.tutorial.websocketdemo.form.SocketRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler implements WebSocketHandler {
   private final ObjectMapper objectMapper;

    private final BinanceClient binanceClient;

    private final List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

    public SocketHandler(BinanceClient binanceClient, ObjectMapper objectMapper) {
        this.binanceClient = binanceClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            SocketRequest request = objectMapper.readValue(message.getPayload(), SocketRequest.class);

            if (request.getType().equals(SocketType.CRYPTO)) {
                BinanceRequest binanceRequest = new BinanceRequest();
                binanceRequest.setId(1L);
                binanceRequest.setMethod(request.getMethod());
                List<String> params = request.getTickers().stream().map((c) -> {
                    return c.toLowerCase() + "@"
                            + Period.getPeriodByTypeAndPeriod(request.getType(), request.getPeriod());
                }).collect(Collectors.toList());
                binanceRequest.setParams(params);

                binanceClient.sendMessage(session, binanceRequest);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("{} is closed", session.getId());
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("{} is connected", session.getId());
        sessions.add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("{} has error {}", session.getId(), exception);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
