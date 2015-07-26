package com.example.xavier.rxexample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bter.api.dto.MarketCompact;
import com.bter.api.dto.MarketDetail;
import com.bter.api.dto.TradingPairId;
import java.util.Map;

public class BterTradingPairArrayAdapter extends ArrayAdapter<TradingPairId>
{
    private Map<TradingPairId, MarketCompact> marketCompactMap;
    private Map<TradingPairId, MarketDetail> marketDetailMap;

    public BterTradingPairArrayAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1);
    }

    @Override public int getCount()
    {
        return super.getCount();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        View marketView = super.getView(position, convertView, parent);
        TradingPairId item = getItem(position);

        final String title;

        final String subTitle1;
        if (marketDetailMap != null)
        {
            MarketDetail marketDetail = marketDetailMap.get(item);
            if (marketDetail != null)
            {
                title = marketDetail.getNiceName();
                subTitle1 = marketDetail.name;
            }
            else
            {
                title = item.key;
                subTitle1 = "null";
            }
        }
        else
        {
            title = item.key;
            subTitle1 = "Loading marketDetail";
        }

        final String subTitle2;
        if (marketCompactMap != null)
        {
            MarketCompact marketInfo = marketCompactMap.get(item);
            if (marketInfo != null)
            {
                subTitle2 = "Fee: " + marketInfo.fee;
            }
            else
            {
                subTitle2 = "null";
            }
        }
        else
        {
            subTitle2 = "Loading marketInfo";
        }

        ((TextView) marketView.findViewById(android.R.id.text1)).setText(title);
        ((TextView) marketView.findViewById(android.R.id.text2)).setText(subTitle1 + " / " + subTitle2);
        return marketView;
    }

    public void setMarketCompactMap(Map<TradingPairId, MarketCompact> marketCompactMap)
    {
        this.marketCompactMap = marketCompactMap;
    }

    public void setMarketDetailMap(Map<TradingPairId, MarketDetail> marketDetailMap)
    {
        this.marketDetailMap = marketDetailMap;
    }
}
