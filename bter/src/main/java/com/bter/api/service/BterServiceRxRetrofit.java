package com.bter.api.service;

import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import java.util.List;
import retrofit.http.GET;
import rx.Observable;

public interface BterServiceRxRetrofit
{
    @GET("/pairs") Observable<List<TradingPairId>> getPairs();

    @GET("/marketinfo") Observable<MarketInfo> getMarketInfo();

    @GET("/marketlist") Observable<MarketList> getMarketList();
}
