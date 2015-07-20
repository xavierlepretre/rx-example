package com.bter.api.dto;

import java.util.HashMap;
import java.util.Map;

public class TradingPairMarketCompactMap extends HashMap<TradingPairId, MarketCompact>
{
    public TradingPairMarketCompactMap()
    {
        super();
    }

    public TradingPairMarketCompactMap(int capacity)
    {
        super(capacity);
    }

    public TradingPairMarketCompactMap(int capacity, float loadFactor)
    {
        super(capacity, loadFactor);
    }

    public TradingPairMarketCompactMap(Map<? extends TradingPairId, ? extends MarketCompact> map)
    {
        super(map);
    }
}
