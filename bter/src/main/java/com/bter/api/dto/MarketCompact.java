package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketCompact
{
    public final double minAmount;
    public final int priceDecimalPlaces;
    public final double fee;

    public MarketCompact(
            @JsonProperty("min_amount") double minAmount,
            @JsonProperty("decimal_places") int priceDecimalPlaces,
            @JsonProperty("fee") double fee)
    {
        this.minAmount = minAmount;
        this.priceDecimalPlaces = priceDecimalPlaces;
        this.fee = fee;
    }
}
