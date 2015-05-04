package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.PriceTicker;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

interface MeloticServiceRxRetrofit
{
    @GET("/markets")
    Observable<MarketMap> getMarkets();

    @GET("/markets/{marketId}/ticker")
    Observable<PriceTicker> getMarketPrice(
            @Path("marketId") String marketId);

    @GET("/markets/{marketId}/buy_depth")
    Observable<List<MarketDepth>> getBuyDepth(
            @Path("marketId") String marketId,
            @Query("count") Integer count);

    @GET("/markets/{marketId}/sell_depth")
    Observable<List<MarketDepth>> getSellDepth(
            @Path("marketId") String marketId,
            @Query("count") Integer count);

    @GET("/markets/{marketId}/deal_orders")
    Observable<List<DealOrder>> getDealOrders(
            @Path("marketId") String marketId,
            @Query("order") String orderBy,
            @Query("count") Integer count,
            @Query("start") Integer startAt);
}
