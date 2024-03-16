package com.tutorial.websocketdemo.enums;

public enum SocketMethod {
    SUBSCRIBE(1, "Đăng ký"),
    UNSUBSCRIBE(2, "Hủy đăng ký");

    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    private SocketMethod(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
