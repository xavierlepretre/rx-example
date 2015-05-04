package com.melotic.api.dto;

import java.util.HashMap;
import java.util.Map;

public class MarketMap extends HashMap<MarketId, Market>
{
    @Override public Market put(MarketId key, Market value)
    {
        if (value != null)
        {
            value.id = key;
        }
        return super.put(key, value);
    }

    @Override public void putAll(Map<? extends MarketId, ? extends Market> map)
    {
        for (Map.Entry<? extends MarketId, ? extends Market> entry : map.entrySet())
        {
            entry.getValue().id = entry.getKey();
        }
        super.putAll(map);
    }
}
