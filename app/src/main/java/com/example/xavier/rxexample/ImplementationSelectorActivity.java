package com.example.xavier.rxexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ImplementationSelectorActivity extends AppCompatActivity
{
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implementation_selector);

        findViewById(android.R.id.text1).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getMeloticCallbackExampleIntent());
            }
        });

        findViewById(android.R.id.text2).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getMeloticRxExampleOkIntent());
            }
        });

        findViewById(R.id.text3).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getMeloticRxExampleFailIntent());
            }
        });

        findViewById(R.id.text4).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getBterCallbackExampleIntent());
            }
        });

        findViewById(R.id.text5).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                startActivity(getBterRxExampleIntent());
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_generic, menu);
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

    private Intent getMeloticCallbackExampleIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListActivity.class.getName());
        return intent;
    }

    private Intent getMeloticRxExampleOkIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListRxOkActivity.class.getName());
        return intent;
    }

    private Intent getMeloticRxExampleFailIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, MeloticMarketListRxFailActivity.class.getName());
        return intent;
    }

    private Intent getBterCallbackExampleIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, BterTradingPairListActivity.class.getName());
        return intent;
    }

    private Intent getBterRxExampleIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(this, BterTradingPairListRxActivity.class.getName());
        return intent;
    }
}
