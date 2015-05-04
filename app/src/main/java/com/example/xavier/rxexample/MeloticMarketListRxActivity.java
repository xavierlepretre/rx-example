package com.example.xavier.rxexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xavier.rx.SimpleAlertDialogOperator;
import com.example.xavier.rxexample.adapter.MarketArrayAdapter;
import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.DealVerb;
import com.melotic.api.dto.Market;
import com.melotic.api.dto.MarketDealOrder;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketDealVerb;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.dto.OrderBy;
import com.melotic.api.service.MeloticServiceRx;
import java.util.Collection;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnItemClickEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.SubscriptionList;

abstract public class MeloticMarketListRxActivity extends ActionBarActivity
{
    protected MeloticServiceRx meloticServiceRx;
    private MarketArrayAdapter arrayAdapter;
    private SubscriptionList subscriptions;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melotic_market_list);
        subscriptions = new SubscriptionList();
        meloticServiceRx = new MeloticServiceRx();
        arrayAdapter = new MarketArrayAdapter(this);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        fetchMarkets();
        listenToList(listView);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_melotic_market_list, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onDestroy()
    {
        subscriptions.unsubscribe();
        subscriptions = new SubscriptionList();
        super.onDestroy();
    }

    private void fetchMarkets()
    {
        subscriptions.add(meloticServiceRx.getMarkets()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>()
                {
                    @Override public void call(Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(), "Failed to fetch markets", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .doOnNext(new Action1<MarketMap>()
                {
                    @Override public void call(MarketMap marketMap)
                    {
                        showMarkets(marketMap);
                    }
                })
                .flatMap(new Func1<MarketMap, Observable<MarketDealOrder>>()
                {
                    @Override public Observable<MarketDealOrder> call(final MarketMap marketMap)
                    {
                        return getLatestDeals(marketMap);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<MarketDealOrder>()
                        {
                            @Override public void call(MarketDealOrder dealOrderPair)
                            {
                                addLatestDeal(dealOrderPair.marketId, dealOrderPair.dealOrder);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                Log.e("MeloticMarketRxActivity", "Failed", throwable);
                            }
                        }));
    }

    public Observable<MarketDealOrder> getLatestDeals(MarketMap marketMap)
    {
        return fetchDeals(
                marketMap.keySet(),
                null,
                1,
                null)
                .flatMap(new Func1<MarketDealOrderList, Observable<MarketDealOrder>>()
                {
                    @Override public Observable<MarketDealOrder> call(MarketDealOrderList marketDealOrderList)
                    {
                        if (marketDealOrderList.dealOrders.size() > 0)
                        {
                            return Observable.just(new MarketDealOrder(marketDealOrderList.marketId, marketDealOrderList.dealOrders.get(0)));
                        }
                        return Observable.just(new MarketDealOrder(marketDealOrderList.marketId, null));
                    }
                });
    }

    abstract protected Observable<MarketDealOrderList> fetchDeals(
            final Collection<MarketId> marketIds,
            final OrderBy orderBy,
            final Integer count,
            final Integer startAt);

    private void showMarkets(MarketMap marketMap)
    {
        arrayAdapter.addAll(marketMap.values());
    }

    private void addLatestDeal(MarketId marketId, DealOrder dealOrder)
    {
        arrayAdapter.putDealOrder(marketId, dealOrder);
    }

    private void listenToList(final ListView listView)
    {
        subscriptions.add(WidgetObservable.itemClicks(listView)
                .map(new Func1<OnItemClickEvent, Market>()
                {
                    @Override public Market call(OnItemClickEvent clickEvent)
                    {
                        return (Market) clickEvent.parent().getItemAtPosition(clickEvent.position());
                    }
                })
                .flatMap(new Func1<Market, Observable<Market>>()
                {
                    @Override public Observable<Market> call(final Market selected)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MeloticMarketListRxActivity.this)
                                .setTitle(selected.title)
                                .setMessage("You clicked " + selected.title);
                        return Observable.create(new SimpleAlertDialogOperator(
                                builder,
                                "More",
                                "Nah"))
                                .filter(new Func1<Integer, Boolean>()
                                {
                                    @Override public Boolean call(Integer button)
                                    {
                                        return button.equals(DialogInterface.BUTTON_POSITIVE);
                                    }
                                })
                                .map(new Func1<Integer, Market>()
                                {
                                    @Override public Market call(Integer integer)
                                    {
                                        return selected;
                                    }
                                });
                    }
                })
                .flatMap(new Func1<Market, Observable<MarketDealVerb>>()
                {
                    @Override public Observable<MarketDealVerb> call(final Market selected)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MeloticMarketListRxActivity.this)
                                .setTitle(selected.title)
                                .setMessage("Buy or Sell depth?");
                        return Observable.create(new SimpleAlertDialogOperator(
                                builder,
                                "Buy",
                                "Sell"))
                                .map(new Func1<Integer, DealVerb>()
                                {
                                    @Override public DealVerb call(Integer button)
                                    {
                                        switch (button)
                                        {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                return DealVerb.buy;
                                        }
                                        return DealVerb.sell;
                                    }
                                })
                                .map(new Func1<DealVerb, MarketDealVerb>()
                                {
                                    @Override public MarketDealVerb call(DealVerb verb)
                                    {
                                        return new MarketDealVerb(selected.id, verb);
                                    }
                                });
                    }
                })
                .subscribe(
                        new Action1<MarketDealVerb>()
                        {
                            @Override public void call(MarketDealVerb marketDealVerb)
                            {
                                handleDepthChosen(marketDealVerb.marketId, marketDealVerb.dealVerb);
                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                Log.e("MeloticMarketListRxActi", "Error", throwable);
                            }
                        }));
    }

    private void handleDepthChosen(MarketId selected, DealVerb verb)
    {
        startActivity(MeloticMarketActivity.getMarketDepthIntent(this, selected, verb));
    }
}
