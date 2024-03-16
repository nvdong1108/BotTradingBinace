package com.tutorial.websocketdemo.enums;

public enum SocketType {
    STOCK(1, "Cổ phiếu"),
    CRYPTO(2, "Tiền điện tử"),
    INDEX(3, "Chỉ số");

    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    private SocketType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
