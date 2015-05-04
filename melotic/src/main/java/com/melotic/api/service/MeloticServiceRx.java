package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.OrderBy;
import com.melotic.api.dto.PriceTicker;
import java.util.Collection;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MeloticServiceRx
{
    private static final int DEFAULT_MAX_CONCURRENT_CALLS = 2;

    final MeloticServiceRxRetrofit service;

    public MeloticServiceRx()
    {
        this(MeloticRestAdapter.builder()
                .build()
                .create(MeloticServiceRxRetrofit.class));
    }

    public MeloticServiceRx(MeloticServiceRxRetrofit service)
    {
        this.service = service;
    }

    public Observable<MarketMap> getMarkets()
    {
        return service.getMarkets();
    }

    public Observable<PriceTicker> getMarketPrice(
            MarketId marketId)
    {
        return service.getMarketPrice(marketId.key);
    }

    public Observable<List<MarketDepth>> getBuyDepth(
            MarketId marketId,
            Integer count)
    {
        return service.getBuyDepth(marketId.key, count);
    }

    public Observable<List<MarketDepth>> getSellDepth(
            MarketId marketId,
            Integer count)
    {
        return service.getSellDepth(marketId.key, count);
    }

    public Observable<MarketDealOrderList> getDealOrders(
            final MarketId marketId,
            OrderBy orderBy,
            Integer count,
            Integer startAt)
    {
        return service.getDealOrders(
                marketId.key,
                orderBy == null ? null : orderBy.apiCode,
                count,
                startAt)
                .map(new Func1<List<DealOrder>, MarketDealOrderList>()
                {
                    @Override public MarketDealOrderList call(List<DealOrder> list)
                    {
                        return new MarketDealOrderList(marketId, list);
                    }
                });
    }

    public Observable<MarketDealOrderList> getManyDealOrdersTooManyRequestsFail(
            Collection<MarketId> marketIds,
            final OrderBy orderBy,
            final Integer count,
            final Integer startAt)
    {
        return Observable.from(marketIds)
                .flatMap(new Func1<MarketId, Observable<MarketDealOrderList>>()
                {
                    @Override public Observable<MarketDealOrderList> call(MarketId marketId)
                    {
                        return getDealOrders(marketId, orderBy, count, startAt);
                    }
                });
    }

    public Observable<MarketDealOrderList> getManyDealOrders(
            final Collection<MarketId> marketIds,
            final OrderBy orderBy,
            final Integer count,
            final Integer startAt)
    {
        return getManyDealOrders(
                marketIds,
                orderBy,
                count,
                startAt,
                DEFAULT_MAX_CONCURRENT_CALLS);
    }

    public Observable<MarketDealOrderList> getManyDealOrders(
            final Collection<MarketId> marketIds,
            final OrderBy orderBy,
            final Integer count,
            final Integer startAt,
            final int maxConcurrent)
    {
        return Observable.from(marketIds)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<MarketId, Observable<MarketDealOrderList>>()
                {
                    @Override public Observable<MarketDealOrderList> call(MarketId marketId)
                    {
                        return getDealOrders(marketId, orderBy, count, startAt);
                    }
                })
                .toList()
                .flatMap(new Func1<List<Observable<MarketDealOrderList>>, Observable<MarketDealOrderList>>()
                {
                    @Override public Observable<MarketDealOrderList> call(
                            List<Observable<MarketDealOrderList>> observables)
                    {
                        return Observable.merge(
                                observables,
                                maxConcurrent);
                    }
                });
    }
}
