package com.example.xavier.rxexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xavier.rxexample.adapter.MarketDepthArrayAdapter;
import com.melotic.api.dto.DealVerb;
import com.melotic.api.dto.MarketDepth;
import com.melotic.api.dto.MarketId;
import com.melotic.api.service.MeloticService;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MeloticMarketActivity extends ActionBarActivity
{
    public static final String EXTRA_MARKET_ID = "marketId";
    public static final String EXTRA_DEAL_VERB = "dealVerb";

    private MarketId marketId;
    private DealVerb verb;
    private MeloticService meloticService;
    private MarketDepthArrayAdapter arrayAdapter;

    public static Intent getMarketDepthIntent(Context context, MarketId marketId, DealVerb verb)
    {
        Intent intent = new Intent();
        intent.setClassName(context, MeloticMarketActivity.class.getName());
        intent.putExtra(MeloticMarketActivity.EXTRA_MARKET_ID, marketId.key);
        intent.putExtra(MeloticMarketActivity.EXTRA_DEAL_VERB, verb.name());
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melotic_market_list);
        meloticService = new MeloticService();
        arrayAdapter = new MarketDepthArrayAdapter(this);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        marketId = new MarketId(getIntent().getStringExtra(EXTRA_MARKET_ID));
        verb = DealVerb.valueOf(getIntent().getStringExtra(EXTRA_DEAL_VERB));
        fetchMarketDepth();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_melotic_market_list, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(getString(R.string.title_market_depth, verb.name(), marketId.key));
        }
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

    private void fetchMarketDepth()
    {
        meloticService.getMarketDepth(
                marketId,
                verb,
                null,
                new Callback<List<MarketDepth>>()
                {
                    @Override public void success(List<MarketDepth> marketMap, Response response)
                    {
                        showMarketDepths(marketMap);
                    }

                    @Override public void failure(RetrofitError error)
                    {
                        Toast.makeText(getApplicationContext(), "Failed to fetch market Depth", Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    private void showMarketDepths(List<MarketDepth> marketDepths)
    {
        arrayAdapter.addAll(marketDepths);
    }
}
