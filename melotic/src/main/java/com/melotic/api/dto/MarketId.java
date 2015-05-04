package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MarketId
{
    public final String key;

    @JsonCreator public MarketId(String key)
    {
        this.key = key;
    }

    @Override public int hashCode()
    {
        return key.hashCode();
    }

    @Override public boolean equals(Object other)
    {
        return (other instanceof MarketId) && key.equals(((MarketId) other).key);
    }
}
