package com.example.xavier.rxexample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.melotic.api.dto.MarketDepth;

public class MarketDepthArrayAdapter extends ArrayAdapter<MarketDepth>
{
    public MarketDepthArrayAdapter(Context context)
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
        MarketDepth item = getItem(position);
        ((TextView) marketView.findViewById(android.R.id.text1)).setText(
                String.format("A: %f, P: %f", item.amount, item.price));

        ((TextView) marketView.findViewById(android.R.id.text2)).setText(
                String.format("Total: %f", item.total));
        return marketView;
    }

    @Override public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override public boolean isEnabled(int position)
    {
        return false;
    }
}
