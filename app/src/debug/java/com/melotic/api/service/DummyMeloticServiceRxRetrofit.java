package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.DummyMarketMap;
import com.melotic.api.dto.DummyPriceTicker;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.PriceTicker;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

class DummyMeloticServiceRxRetrofit implements MeloticServiceRxRetrofit
{
    private static final int DEFAULT_DELAY_MILLISEC = 500;

    @Override public Observable<MarketMap> getMarkets()
    {
        return Observable.<MarketMap>just(new DummyMarketMap())
                .observeOn(Schedulers.io())
                .delay(DEFAULT_DELAY_MILLISEC, TimeUnit.MILLISECONDS);

    }

    @Override public Observable<PriceTicker> getMarketPrice(
            @Path("marketId") String marketId)
    {
        return Observable.<PriceTicker>just(new DummyPriceTicker());
    }

    @Override public Observable<List<MarketDepth>> getBuyDepth(
            String marketId,
            Integer count)
    {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override public Observable<List<MarketDepth>> getSellDepth(
            String marketId,
            Integer count)
    {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override public Observable<List<DealOrder>> getDealOrders(
            String marketId,
            String orderBy,
            Integer count,
            Integer startAt)
    {
        throw new IllegalArgumentException("Not implemented");
    }
}
