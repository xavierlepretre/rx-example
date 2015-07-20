package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceTicker
{
    public final double highestBid;
    public final double highestDealPrice;
    public final double latestPrice;
    public final double lowestAsk;
    public final double lowestDealPrice;
    public final double volume;
    public final double priceChange;

    public PriceTicker(
            @JsonProperty("highest_bid") double highestBid,
            @JsonProperty("highest_deal_price") double highestDealPrice,
            @JsonProperty("latest_price") double latestPrice,
            @JsonProperty("lowest_ask") double lowestAsk,
            @JsonProperty("lowest_deal_price") double lowestDealPrice,
            @JsonProperty("volume") double volume,
            @JsonProperty("price_change") double priceChange)
    {
        this.highestBid = highestBid;
        this.highestDealPrice = highestDealPrice;
        this.latestPrice = latestPrice;
        this.lowestAsk = lowestAsk;
        this.lowestDealPrice = lowestDealPrice;
        this.volume = volume;
        this.priceChange = priceChange;
    }
}
