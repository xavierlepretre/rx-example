package com.example.xavier.rxexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.bter.action.MoreInfoChoice;
import com.bter.api.dto.MarketDetail;
import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import com.bter.api.service.BterServiceRx;
import com.example.xavier.rx.SimpleAlertDialogOperator;
import com.example.xavier.rxexample.adapter.BterTradingPairArrayAdapter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnItemClickEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.internal.util.SubscriptionList;
import rx.observables.ConnectableObservable;

public class BterTradingPairListRxActivity extends AppCompatActivity
{
    protected BterServiceRx bterServiceRx;
    private BterTradingPairArrayAdapter arrayAdapter;
    private SubscriptionList subscriptions;
    ConnectableObservable<MarketList> sharedMarketInfoObservable;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bter_market_list);
        subscriptions = new SubscriptionList();
        bterServiceRx = new BterServiceRx();
        arrayAdapter = new BterTradingPairArrayAdapter(this);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        sharedMarketInfoObservable = fetchAndShowMarketList().publish();
        fetchTradingPairs();
        listenToList(listView);
        sharedMarketInfoObservable.connect();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_generic, menu);
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
        sharedMarketInfoObservable = null;
        subscriptions.unsubscribe();
        subscriptions = new SubscriptionList();
        super.onDestroy();
    }

    private void fetchTradingPairs()
    {
        subscriptions.add(
                Observable.combineLatest(
                        fetchAndShowTradingPairs(),
                        fetchAndShowMarketInfo(),
                        sharedMarketInfoObservable,
                        new Func3<List<TradingPairId>, MarketInfo, MarketList, Object>()
                        {
                            @Override public Object call(List<TradingPairId> tradingPairIds, MarketInfo marketInfo, MarketList marketList)
                            {
                                return null;
                            }
                        })
                        .subscribe(
                                new Action1<Object>()
                                {
                                    @Override public void call(Object ignored)
                                    {
                                    }
                                },
                                new Action1<Throwable>()
                                {
                                    @Override public void call(Throwable throwable)
                                    {
                                        Log.e("BterTradingPairListRxAc", "Failed", throwable);
                                    }
                                }));
    }

    private Observable<List<TradingPairId>> fetchAndShowTradingPairs()
    {
        return bterServiceRx.getPairs()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>()
                {
                    @Override public void call(Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(), "Failed to fetch trading pairs", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .doOnNext(new Action1<List<TradingPairId>>()
                {
                    @Override public void call(List<TradingPairId> tradingPairIds)
                    {
                        arrayAdapter.setNotifyOnChange(false);
                        arrayAdapter.clear();
                        arrayAdapter.addAll(tradingPairIds);
                        arrayAdapter.setNotifyOnChange(true);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Observable<MarketInfo> fetchAndShowMarketInfo()
    {
        return bterServiceRx.getMarketInfo()
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>()
                {
                    @Override public void call(Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(), "Failed to fetch market info", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .onErrorResumeNext(Observable.<MarketInfo>empty())
                .doOnNext(new Action1<MarketInfo>()
                {
                    @Override public void call(MarketInfo marketInfo)
                    {
                        arrayAdapter.setMarketCompactMap(marketInfo.pairs);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Observable<MarketList> fetchAndShowMarketList()
    {
        return bterServiceRx.getMarketList()
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>()
                {
                    @Override public void call(Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Failed to fetch market list",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .onErrorResumeNext(Observable.<MarketList>empty())
                .doOnNext(new Action1<MarketList>()
                {
                    @Override public void call(MarketList marketList)
                    {
                        arrayAdapter.setMarketDetailMap(marketList.data);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void listenToList(final ListView listView)
    {
        subscriptions.add(
                sharedMarketInfoObservable
                        .flatMap(new Func1<MarketList, Observable<Pair<TradingPairId, MoreInfoChoice>>>()
                        {
                            @Override public Observable<Pair<TradingPairId, MoreInfoChoice>> call(final MarketList marketList)
                            {
                                return WidgetObservable.itemClicks(listView)
                                        .flatMap(new Func1<OnItemClickEvent, Observable<TradingPairId>>()
                                        {
                                            @Override public Observable<TradingPairId> call(OnItemClickEvent clickEvent)
                                            {
                                                TradingPairId selected = (TradingPairId) clickEvent.parent().getItemAtPosition(clickEvent.position());
                                                return askIfWantMore(selected, marketList.data.get(selected));
                                            }
                                        })
                                        .flatMap(new Func1<TradingPairId, Observable<Pair<TradingPairId, MoreInfoChoice>>>()
                                        {
                                            @Override public Observable<Pair<TradingPairId, MoreInfoChoice>> call(TradingPairId tradingPairId)
                                            {
                                                return askWhichInfo(tradingPairId, marketList.data.get(tradingPairId));
                                            }
                                        });
                            }
                        })
                        .subscribe(
                                new Action1<Pair<TradingPairId, MoreInfoChoice>>()
                                {
                                    @Override public void call(Pair<TradingPairId, MoreInfoChoice> tradingPairId)
                                    {
                                        Toast.makeText(BterTradingPairListRxActivity.this,
                                                "You chose to see " + tradingPairId.second.name() + " for " + tradingPairId.first.key,
                                                Toast.LENGTH_LONG)
                                                .show();
                                        // TODO start relevant Activity
                                    }
                                },
                                new Action1<Throwable>()
                                {
                                    @Override public void call(Throwable throwable)
                                    {
                                        Log.e("BterTradingPairListRxAc", "Error", throwable);
                                    }
                                }));
    }

    protected Observable<TradingPairId> askIfWantMore(final TradingPairId selected, MarketDetail detail)
    {
        String name = detail != null
                ? detail.getNiceName()
                : selected.key;
        AlertDialog.Builder builder = new AlertDialog.Builder(BterTradingPairListRxActivity.this)
                .setTitle(name)
                .setMessage("You clicked " + name);
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
                .map(new Func1<Integer, TradingPairId>()
                {
                    @Override public TradingPairId call(Integer integer)
                    {
                        return selected;
                    }
                });
    }

    protected Observable<Pair<TradingPairId, MoreInfoChoice>> askWhichInfo(final TradingPairId selected, final MarketDetail detail)
    {
        String name = detail != null
                ? detail.getNiceName()
                : selected.key;
        AlertDialog.Builder builder = new AlertDialog.Builder(BterTradingPairListRxActivity.this)
                .setTitle(name)
                .setMessage("Which detail do you want to see?");
        return Observable.create(new SimpleAlertDialogOperator(
                builder,
                "Depth",
                "Trade History"))
                .map(new Func1<Integer, Pair<TradingPairId, MoreInfoChoice>>()
                {
                    @Override public Pair<TradingPairId, MoreInfoChoice> call(Integer button)
                    {
                        return Pair.create(
                                selected,
                                button.equals(DialogInterface.BUTTON_POSITIVE)
                                        ? MoreInfoChoice.DEPTH
                                        : MoreInfoChoice.TRADE_HISTORY);
                    }
                });
    }
}
