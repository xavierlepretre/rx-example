package com.example.xavier.rxexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ImplementationSelectorActivity extends ActionBarActivity
{
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implementation_selector);

        findViewById(android.R.id.text1).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getCallbackExampleIntent());
            }
        });

        findViewById(android.R.id.text2).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getRxExampleOkIntent());
            }
        });

        findViewById(R.id.text3).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getRxExampleFailIntent());
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_melotic_market_list, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(R.string.selector_title);
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

    private Intent getCallbackExampleIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListActivity.class.getName());
        return intent;
    }

    private Intent getRxExampleOkIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListRxOkActivity.class.getName());
        return intent;
    }

    private Intent getRxExampleFailIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListRxFailActivity.class.getName());
        return intent;
    }
}
