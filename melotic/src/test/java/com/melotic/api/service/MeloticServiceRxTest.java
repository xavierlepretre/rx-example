package com.melotic.api.service;

import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeloticServiceRxTest
{
    AtomicInteger requestCount;

    @Before
    public void setUp()
    {
        requestCount = new AtomicInteger(0);
    }

    private MeloticServiceRxRetrofit mockRejectConcurrentService()
    {
        MeloticServiceRxRetrofit rxRetrofit = mock(MeloticServiceRxRetrofit.class);

        when(rxRetrofit.getDealOrders(any(String.class), any(String.class), any(Integer.class), any(Integer.class)))
                .thenAnswer(new Answer<Object>()
                {
                    @Override public Object answer(InvocationOnMock invocation) throws Throwable
                    {
                        Observable<List<DealOrder>> observable = null;
                        if (requestCount.incrementAndGet() > 1)
                        {
                            observable = Observable.just(1)
                                    .subscribeOn(Schedulers.newThread())
                                    .delay(1000, TimeUnit.MILLISECONDS)
                                    .flatMap(new Func1<Integer, Observable<List<DealOrder>>>()
                                    {
                                        @Override public Observable<List<DealOrder>> call(Integer integer)
                                        {
                                            return Observable.error(new IllegalArgumentException("Too early"));
                                        }
                                    });
                        }
                        else
                        {
                            String id = (String) invocation.getArguments()[0];
                            List<DealOrder> dealOrders = new ArrayList<>();
                            DealOrder dealOrder = new DealOrder();
                            dealOrder.marketId = new MarketId(id);
                            dealOrders.add(dealOrder);
                            observable = Observable.just(dealOrders)
                                    .subscribeOn(Schedulers.newThread())
                                    .delay(1000, TimeUnit.MILLISECONDS);
                        }
                        return observable
                                .finallyDo(new Action0()
                                {
                                    @Override public void call()
                                    {
                                        requestCount.getAndDecrement();
                                    }
                                });
                    }
                });
        return rxRetrofit;
    }

    @Test
    public void testServiceRejectsConcurrentCalls() throws InterruptedException
    {
        final MeloticServiceRx rejectConcurrentCallService = new MeloticServiceRx(mockRejectConcurrentService());
        final MarketDealOrderList[] expected1 = new MarketDealOrderList[1];
        final Throwable[] expected2 = new Throwable[1];

        List<MarketId> marketIds = new ArrayList<>();
        marketIds.add(new MarketId("id1"));
        marketIds.add(new MarketId("id2"));
        rejectConcurrentCallService.getManyDealOrdersTooManyRequestsFail(marketIds, null, null, null)
                .doOnNext(new Action1<MarketDealOrderList>()
                {
                    @Override public void call(MarketDealOrderList marketDealOrderList)
                    {
                        expected1[0] = marketDealOrderList;
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MarketDealOrderList>>()
                {
                    @Override public Observable<? extends MarketDealOrderList> call(Throwable throwable)
                    {
                        expected2[0] = throwable;
                        return Observable.empty();
                    }
                })
                .toBlocking().getIterator();
        assertThat(expected1[0]).isNotNull();
        assertThat(expected2[0]).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testManyDealsError()
    {
        final MeloticServiceRx rejectConcurrentCallService = new MeloticServiceRx(mockRejectConcurrentService());
        List<MarketId> marketIds = new ArrayList<>();
        marketIds.add(new MarketId("id1"));
        marketIds.add(new MarketId("id2"));
        rejectConcurrentCallService.getManyDealOrders(marketIds, null, null, null, 2)
                .toBlocking()
                .getIterator();
    }

    @Test public void testManyDealsNoError()
    {
        final MeloticServiceRx rejectConcurrentCallService = new MeloticServiceRx(mockRejectConcurrentService());
        List<MarketId> marketIds = new ArrayList<>();
        marketIds.add(new MarketId("id1"));
        marketIds.add(new MarketId("id2"));
        Iterator<MarketDealOrderList> dealOrders =
                rejectConcurrentCallService.getManyDealOrders(marketIds, null, null, null, 1)
                        .toBlocking()
                        .toIterable()
                        .iterator();

        MarketDealOrderList order1 = dealOrders.next();
        assertThat(order1.marketId).isEqualTo(new MarketId("id1"));
        assertThat(order1.dealOrders.size()).isEqualTo(1);
        assertThat(order1.dealOrders.get(0).marketId).isEqualTo(new MarketId("id1"));
        MarketDealOrderList order2 = dealOrders.next();
        assertThat(order2.marketId).isEqualTo(new MarketId("id2"));
        assertThat(order2.dealOrders.size()).isEqualTo(1);
        assertThat(order2.dealOrders.get(0).marketId).isEqualTo(new MarketId("id2"));
    }
}
