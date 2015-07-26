package com.example.xavier.rxexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.bter.action.MoreInfoChoice;
import com.bter.api.dto.MarketDetail;
import com.bter.api.dto.MarketInfo;
import com.bter.api.dto.MarketList;
import com.bter.api.dto.TradingPairId;
import com.bter.api.service.BterService;
import com.example.xavier.rxexample.adapter.BterTradingPairArrayAdapter;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BterTradingPairListActivity extends AppCompatActivity
{
    private BterService bterService;
    private BterTradingPairArrayAdapter arrayAdapter;
    private MarketList marketList;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bter_market_list);
        bterService = new BterService();
        arrayAdapter = new BterTradingPairArrayAdapter(this);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        fetchAndShowTradingPairs();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                askIfWantMore((TradingPairId) parent.getItemAtPosition(position));
            }
        });
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

    private void fetchAndShowTradingPairs()
    {
        bterService.getPairs(new Callback<List<TradingPairId>>()
        {
            @Override public void success(List<TradingPairId> tradingPairIds, Response response)
            {
                arrayAdapter.setNotifyOnChange(false);
                arrayAdapter.clear();
                arrayAdapter.addAll(tradingPairIds);
                arrayAdapter.setNotifyOnChange(true);
                arrayAdapter.notifyDataSetChanged();

                fetchAndShowMarketInfo();
            }

            @Override public void failure(RetrofitError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to fetch trading pairs", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void fetchAndShowMarketInfo()
    {
        bterService.getMarketInfo(new Callback<MarketInfo>()
        {
            @Override public void success(MarketInfo marketInfo, Response response)
            {
                arrayAdapter.setMarketCompactMap(marketInfo.pairs);
                arrayAdapter.notifyDataSetChanged();

                fetchMarketList();
            }

            @Override public void failure(RetrofitError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to fetch market info", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void fetchMarketList()
    {
        bterService.getMarketList(new Callback<MarketList>()
        {
            @Override public void success(MarketList marketList, Response response)
            {
                // We need to save it here to show more info in dialog
                BterTradingPairListActivity.this.marketList = marketList;
                arrayAdapter.setMarketDetailMap(marketList.data);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override public void failure(RetrofitError error)
            {
                Toast.makeText(getApplicationContext(), "Failed to fetch market list", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void askIfWantMore(final TradingPairId selected)
    {
        MarketDetail detail = marketList != null
                ? marketList.data.get(selected)
                : null;
        String name = detail != null
                ? detail.getNiceName()
                : selected.key;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(name)
                .setMessage("You clicked " + name)
                .setPositiveButton(
                        "More",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                askWhichInfo(selected);
                            }
                        })
                .setNegativeButton("Nah", null);
        dialogBuilder.create().show();
    }

    private void askWhichInfo(final TradingPairId selected)
    {
        MarketDetail detail = marketList != null
                ? marketList.data.get(selected)
                : null;
        String name = detail != null
                ? detail.getNiceName()
                : selected.key;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(name)
                .setMessage("Which detail do you want to see?")
                .setPositiveButton(
                        "Depth",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(BterTradingPairListActivity.this,
                                        "You chose to see " + MoreInfoChoice.DEPTH.name() + " for " + selected.key,
                                        Toast.LENGTH_LONG)
                                        .show();
                                // TODO start relevant Activity
                            }
                        })
                .setNegativeButton(
                        "Trade History",
                        new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(BterTradingPairListActivity.this,
                                        "You chose to see " + MoreInfoChoice.TRADE_HISTORY.name() + " for " + selected.key,
                                        Toast.LENGTH_LONG)
                                        .show();
                                // TODO start relevant Activity
                            }
                        });
        dialogBuilder.create().show();
    }
}
