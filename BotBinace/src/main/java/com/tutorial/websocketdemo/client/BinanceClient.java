package com.tutorial.websocketdemo.client;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.websocketdemo.data.BinanceKlineData;
import com.tutorial.websocketdemo.enums.SocketMethod;
import com.tutorial.websocketdemo.form.BinanceRequest;
import com.tutorial.websocketdemo.service.CryptoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BinanceClient extends WebSocketClient {

    @Value("${app.url.binance.websocket}")
    private String binanceWsUrl;

    private final CryptoService cryptoService;

    private final ObjectMapper objectMapper;

    private HashSet<String> params = new HashSet<>();

    private Map<WebSocketSession, HashSet<String>> sessions = new LinkedHashMap<>();

    public BinanceClient(CryptoService cryptoService, ObjectMapper objectMapper) {
        super(URI.create("wss://stream.binance.com/stream"));
        this.connect();
        this.cryptoService = cryptoService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("Binance socket is connected");
    }

    @Override
    public void onMessage(String message) {
        try {
            BinanceKlineData data = objectMapper.readValue(message, BinanceKlineData.class);

            long startTime = data.getData().getKline().getStartTime();
            data.getData().getKline().setStartTime(startTime);
            cryptoService.add(data);

            for (Map.Entry<WebSocketSession, HashSet<String>> entry : sessions.entrySet()) {
                WebSocketSession session = entry.getKey();
                HashSet<String> sessionParams = entry.getValue();

                if (session.isOpen() && sessionParams.contains(data.getStream())) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("Binance is closed, code: " + code + ", reason: " + reason);
        scheduleReconnect(3000);
    }

    private void scheduleReconnect(long delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                reconnect();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }).start();
    }

    @Override
    public void onError(Exception ex) {
        log.info("Binance has error: " + ex.getMessage());
    }

    public void sendMessage(WebSocketSession session, BinanceRequest request) {
        try {
            if (this.sessions.containsKey(session) && session.isOpen()) {
                HashSet<String> params = this.sessions.get(session);

                if (request.getMethod().equals(SocketMethod.SUBSCRIBE)) {
                    params.addAll(request.getParams());
                } else {
                    params.removeAll(request.getParams());
                }

                this.sessions.put(session, params);
            } else {
                this.sessions.put(session, new HashSet<>(request.getParams()));
            }

            if (request.getMethod().equals(SocketMethod.SUBSCRIBE)) {
                params.addAll(request.getParams());
                this.send(objectMapper.writeValueAsString(request));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
