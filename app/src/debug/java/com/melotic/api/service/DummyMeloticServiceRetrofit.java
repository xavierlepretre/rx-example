package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.PriceTicker;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

class DummyMeloticServiceRetrofit implements MeloticServiceRetrofit
{
    private final DummyMeloticServiceRxRetrofit innerService;

    public DummyMeloticServiceRetrofit()
    {
        this.innerService = new DummyMeloticServiceRxRetrofit();
    }

    @Override public void getMarkets(final Callback<MarketMap> callback)
    {
        innerService.getMarkets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<MarketMap>()
                        {
                            @Override public void call(MarketMap marketMap)
                            {
                                callback.success(marketMap, null);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                callback.failure(RetrofitError.unexpectedError("Calling getMarkets", throwable));
                            }
                        });
    }

    @Override public void getMarketPrice(final String marketId, final Callback<PriceTicker> callback)
    {
        innerService.getMarketPrice(marketId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<PriceTicker>()
                        {
                            @Override public void call(PriceTicker priceTicker)
                            {
                                callback.success(priceTicker, null);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                callback.failure(RetrofitError.unexpectedError("Calling getMarketPrice on " + marketId, throwable));
                            }
                        });
    }

    @Override public void getBuyDepth(final String marketId, final Integer count, final Callback<List<MarketDepth>> callback)
    {
        innerService.getBuyDepth(marketId, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<MarketDepth>>()
                        {
                            @Override public void call(List<MarketDepth> marketDepths)
                            {
                                callback.success(marketDepths, null);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                callback.failure(RetrofitError.unexpectedError("Calling getBuyDepth on " + marketId + " with count " + count, throwable));
                            }
                        });
    }

    @Override public void getSellDepth(final String marketId, final Integer count, final Callback<List<MarketDepth>> callback)
    {
        innerService.getSellDepth(marketId, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<MarketDepth>>()
                        {
                            @Override public void call(List<MarketDepth> marketDepths)
                            {
                                callback.success(marketDepths, null);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                callback.failure(RetrofitError.unexpectedError("Calling getSellDepth on " + marketId + " with count " + count, throwable));
                            }
                        });
    }

    @Override public void getDealOrders(final String marketId, final String orderBy, final Integer count,
            final Integer startAt, final Callback<List<DealOrder>> callback)
    {
        innerService.getDealOrders(marketId, orderBy, count, startAt)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DealOrder>>()
                        {
                            @Override public void call(List<DealOrder> dealOrders)
                            {
                                callback.success(dealOrders, null);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                callback.failure(RetrofitError.unexpectedError("Calling getDealOrders on " + marketId
                                        + " with orderBy " +orderBy
                                        + " with count " + count
                                        + " with startAt" + startAt, throwable));
                            }
                        });
    }
}
