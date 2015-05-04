package com.example.xavier.rxexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xavier.rxexample.adapter.MarketArrayAdapter;
import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.DealVerb;
import com.melotic.api.dto.Market;
import com.melotic.api.dto.MarketDealOrderList;
import com.melotic.api.dto.MarketId;
import com.melotic.api.dto.MarketMap;
import com.melotic.api.service.MeloticService;
import java.util.Iterator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MeloticMarketListActivity extends ActionBarActivity
{
    private MeloticService meloticService;
    private MarketArrayAdapter arrayAdapter;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melotic_market_list);
        meloticService = new MeloticService();
        arrayAdapter = new MarketArrayAdapter(this);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                handleItemClicked((Market) parent.getItemAtPosition(position));
            }
        });
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

    @Override protected void onStart()
    {
        super.onStart();
        fetchMarkets();
    }

    private void fetchMarkets()
    {
        meloticService.getMarkets(new Callback<MarketMap>()
        {
            @Override public void success(MarketMap marketMap, Response response)
            {
                showMarkets(marketMap);
            }

            @Override public void failure(RetrofitError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to fetch markets", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void showMarkets(MarketMap marketMap)
    {
        arrayAdapter.addAll(marketMap.values());
        fetchLatestOrders(marketMap);
    }

    private void fetchLatestOrders(MarketMap marketMap)
    {
        fetchNext(marketMap.keySet().iterator());
    }

    private void fetchNext(Iterator<MarketId> iterator)
    {
        if (iterator.hasNext())
        {
            fetchLatestDealOrder(iterator.next(), iterator);
        }
    }

    private void fetchLatestDealOrder(final MarketId marketId, final Iterator<MarketId> remaining)
    {
        meloticService.getDealOrders(
                marketId,
                null,
                1,
                null,
                new Callback<MarketDealOrderList>()
                {
                    @Override public void success(MarketDealOrderList dealOrders, Response response)
                    {
                        if (dealOrders.dealOrders.size() > 0)
                        {
                            addLatestDeal(marketId, dealOrders.dealOrders.get(0));
                        }
                        fetchNext(remaining);
                    }

                    @Override public void failure(RetrofitError error)
                    {
                        Toast.makeText(getApplicationContext(), "Failed to fetch latest deal order for " + marketId.key, Toast.LENGTH_LONG)
                                .show();
                        fetchNext(remaining);
                    }
                });
    }

    private void addLatestDeal(MarketId marketId, DealOrder dealOrder)
    {
        arrayAdapter.putDealOrder(marketId, dealOrder);
    }

    private void handleItemClicked(final Market selected)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(selected.title)
                .setMessage("You clicked " + selected.title)
                .setPositiveButton(
                        "More",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                handleMoreInfoClicked(selected);
                            }
                        })
                .setNegativeButton("Nah", null);
        dialogBuilder.create().show();
    }

    private void handleMoreInfoClicked(final Market selected)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(selected.title)
                .setMessage("Buy or Sell depth?")
                .setPositiveButton(
                        "Buy",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                handleDepthChosen(selected.id, DealVerb.buy);
                            }
                        })
                .setNegativeButton(
                        "Sell",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                handleDepthChosen(selected.id, DealVerb.sell);
                            }
                        });
        dialogBuilder.create().show();
    }

    private void handleDepthChosen(MarketId selected, DealVerb verb)
    {
        startActivity(MeloticMarketActivity.getMarketDepthIntent(this, selected, verb));
    }
}
