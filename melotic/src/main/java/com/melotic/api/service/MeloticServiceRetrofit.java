package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.PriceTicker;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

interface MeloticServiceRetrofit
{
    @GET("/markets")
    void getMarkets(Callback<MarketMap> callback);

    @GET("/markets/{marketId}/ticker")
    void getMarketPrice(
            @Path("marketId") String marketId,
            Callback<PriceTicker> callback);

    @GET("/markets/{marketId}/buy_depth")
    void getBuyDepth(
            @Path("marketId") String marketId,
            @Query("count") Integer count,
            Callback<List<MarketDepth>> callback);

    @GET("/markets/{marketId}/sell_depth")
    void getSellDepth(
            @Path("marketId") String marketId,
            @Query("count") Integer count,
            Callback<List<MarketDepth>> callback);

    @GET("/markets/{marketId}/deal_orders")
    void getDealOrders(
            @Path("marketId") String marketId,
            @Query("order") String orderBy,
            @Query("count") Integer count,
            @Query("start") Integer startAt,
            Callback<List<DealOrder>> callback);
}

