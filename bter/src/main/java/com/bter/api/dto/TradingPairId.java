package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TradingPairId
{
    public final String key;

    @JsonCreator public TradingPairId(String key)
    {
        this.key = key;
    }

    @Override public int hashCode()
    {
        return key.hashCode();
    }

    @Override public boolean equals(Object other)
    {
        return (other instanceof TradingPairId) && key.equals(((TradingPairId) other).key);
    }
}
