package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DealOrderId
{
    public final Integer key;

    @JsonCreator public DealOrderId(int key)
    {
        this.key = key;
    }

    @Override public int hashCode()
    {
        return key.hashCode();
    }

    @Override public boolean equals(Object other)
    {
        return (other instanceof DealOrderId) && key.equals(((DealOrderId) other).key);
    }
}
