package me.brandonmorrissey.deepseafishing.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.brandonmorrissey.deepseafishing.R;

/**
 * Created by: Brandon Morrissey
 * Date: 02/21/2016
 * BookingListViewHolder class for the Adapter
 */
public class BookingListViewHolder extends RecyclerView.ViewHolder {
    protected TextView textView;

    public BookingListViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.title);
    }
}
