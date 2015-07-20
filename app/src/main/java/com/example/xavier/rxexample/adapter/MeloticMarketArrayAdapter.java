package com.example.xavier.rxexample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.melotic.api.dto.DealOrder;
import com.melotic.api.dto.Market;
import com.melotic.api.dto.MarketId;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MeloticMarketArrayAdapter extends ArrayAdapter<Market>
{
    private final Map<MarketId, DealOrder> latestDealOrders;
    private final DateFormat dateFormat;

    public MeloticMarketArrayAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1);
        this.latestDealOrders = new HashMap<>();
        this.dateFormat = SimpleDateFormat.getDateTimeInstance();
    }

    @Override public int getCount()
    {
        return super.getCount();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        View marketView = super.getView(position, convertView, parent);
        Market item = getItem(position);
        ((TextView) marketView.findViewById(android.R.id.text1)).setText(item.title);

        String subTitle = item.id.key;
        if (!latestDealOrders.containsKey(item.id))
        {
            subTitle += ", fetching latest deal";
        }
        else
        {
            DealOrder latestDeal = latestDealOrders.get(item.id);
            if (latestDeal != null)
            {
                subTitle += ", latest: " + dateFormat.format(latestDeal.dealtAt);
            }
            else
            {
                subTitle += ", no latest deal";
            }
        }

        ((TextView) marketView.findViewById(android.R.id.text2)).setText(subTitle);
        return marketView;
    }

    public void putDealOrder(MarketId marketId, DealOrder dealOrder)
    {
        latestDealOrders.put(marketId, dealOrder);
        notifyDataSetChanged();
    }
}
