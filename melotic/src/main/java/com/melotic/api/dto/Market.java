package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Market extends PriceTicker
{
    @JsonIgnore
    public MarketId id;

    public String title;
}
