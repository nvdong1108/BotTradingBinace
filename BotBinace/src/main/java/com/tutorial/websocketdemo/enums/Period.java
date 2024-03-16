package com.tutorial.websocketdemo.enums;

public enum Period {
    // crypto
    kline_1m(1, SocketType.CRYPTO, "1m"),
    kline_3m(2, SocketType.CRYPTO, "3m"),
    kline_5m(3, SocketType.CRYPTO, "5m"),
    kline_15m(4, SocketType.CRYPTO, "15m"),
    kline_30m(5, SocketType.CRYPTO, "30m"),
    kline_1h(6, SocketType.CRYPTO, "1h"),
    kline_2h(7, SocketType.CRYPTO, "2h"),
    kline_4h(8, SocketType.CRYPTO, "4h"),
    kline_6h(9, SocketType.CRYPTO, "6h"),
    kline_8h(10, SocketType.CRYPTO, "8h"),
    kline_1d(11, SocketType.CRYPTO, "1d"),
    kline_3d(12, SocketType.CRYPTO, "3d"),
    kline_1w(13, SocketType.CRYPTO, "1w"),
    kline_1M(14, SocketType.CRYPTO, "1M"),

    // stock
    stock_1m(15, SocketType.STOCK, "1m"),
    stock_3m(16, SocketType.STOCK, "3m"),
    stock_5m(17, SocketType.STOCK, "5m"),
    stock_15m(18, SocketType.STOCK, "15m"),
    stock_30m(19, SocketType.STOCK, "30m"),
    stock_1h(20, SocketType.STOCK, "1h"),
    stock_2h(21, SocketType.STOCK, "2h"),
    stock_4h(22, SocketType.STOCK, "4h"),
    stock_6h(23, SocketType.STOCK, "6h"),
    stock_8h(24, SocketType.STOCK, "8h"),
    stock_1d(25, SocketType.STOCK, "1d"),
    stock_3d(26, SocketType.STOCK, "3d"),
    stock_1w(27, SocketType.STOCK, "1w"),
    stock_1M(28, SocketType.STOCK, "1M");

    private Integer id;
    private SocketType type;
    private String period;

    public Integer getId() {
        return id;
    }

    public SocketType getType() {
        return type;
    }

    public String getPeriod() {
        return period;
    }

    private Period(Integer id, SocketType type, String period) {
        this.id = id;
        this.type = type;
        this.period = period;
    }

    public static Period getPeriodByTypeAndPeriod(SocketType type, String period) {
        for (Period p : Period.values()) {
            if (p.getType().equals(type) && p.getPeriod().equals(period)) {
                return p;
            }
        }
        return null;
    }

}
