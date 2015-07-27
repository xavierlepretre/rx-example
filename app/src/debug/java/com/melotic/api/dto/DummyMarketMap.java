package com.melotic.api.dto;

public class DummyMarketMap extends MarketMap
{
    public DummyMarketMap()
    {
        put(new MarketId("bc-btc"),
                new Market("BC/BTC", 0.3, 0.2, 0.1, 0.1, 0.2, 123, 0.00527815));
        put(new MarketId("xmr-btc"),
                new Market("XMR/BTC", 0.3, 0.2, 0.1, 0.1, 0.2, 123, -0.06217274));
    }
}
