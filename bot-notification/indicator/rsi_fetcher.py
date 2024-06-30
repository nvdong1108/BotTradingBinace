import ccxt
import pandas as pd
import ta
import time
import asyncio
from telegram import Bot
from datetime import datetime

API_TOKEN = "6837177530:AAFUTQeVB7zf7pR6z_8iJFZYxxY7WYdLSN4"
CHAT_ID = "-4251336843"

def fetch_ohlcv(symbol, timeframe, limit=100):
    binance = ccxt.binance()
    ohlcv = binance.fetch_ohlcv(symbol, timeframe, limit=limit)
    df = pd.DataFrame(ohlcv, columns=['timestamp', 'open', 'high', 'low', 'close', 'volume'])
    df['timestamp'] = pd.to_datetime(df['timestamp'], unit='ms')
    return df


def calculate_rsi(df, period=14):
    df['rsi'] = ta.momentum.RSIIndicator(df['close'], window=period).rsi()
    return df


async def send(message):
    bot = Bot(token=API_TOKEN)
    await bot.send_message(chat_id=CHAT_ID, text=message)


def get_current_btc_usdt_price():
    binance = ccxt.binance()
    try:
        ticker = binance.fetch_ticker('BTC/USDT')
        current_price = ticker['last']
        return current_price
    except Exception as e:
        print(f"Error fetching BTC/USDT price: {e}")
        return None


async def new_order(rsi,side):
    print(f"open {side}")
    btc_usdt_price = None
    index = 0
    while index < 3 and btc_usdt_price is None:
        btc_usdt_price = get_current_btc_usdt_price()
    if btc_usdt_price is None:
        print("not get price BTC ")
    formatted_price = f"{int(btc_usdt_price):,} USDT"

    formatted_rsi = f"{rsi:.2f}"
    current_time = datetime.now()
    formatted_time = current_time.strftime("%Y/%m/%d %H:%M:%S")
    message = (
        "🚨🚨🚨\n\n"
        f"Timestamp: {formatted_time}\n\n"
        f"RSI: {formatted_rsi}\n\n"
        f"Action: {side}\n\n"
        f"Entry Price: {formatted_price}\n\n"
        ""
    )
    await send(message)


async def main(symbol='BTC/USDT', period=14, interval=60):
    while True:
        df_15m = fetch_ohlcv(symbol, '15m')
        df_15m = calculate_rsi(df_15m, period)
        current_rsi_15m = df_15m['rsi'].iloc[-1]

        message = f"RSI Alert! Current RSI for {symbol} on 15m is {current_rsi_15m:.2f}"
        print(message)
        k = 25
        if current_rsi_15m < (50-k):
            await new_order(current_rsi_15m,"BUY")
        if current_rsi_15m > (50+k):
            await new_order(current_rsi_15m,"SELL")
        time.sleep(interval)


if __name__ == "__main__":
    asyncio.run(main())
