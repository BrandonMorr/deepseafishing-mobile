package me.brandonmorrissey.deepseafishing.util;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import me.brandonmorrissey.deepseafishing.R;
import me.brandonmorrissey.deepseafishing.model.Booking;

/**
 * Created by: Brandon Morrissey
 * Date: 02/21/2016
 * BookingRecyclerAdapter adapter class for RecyclerView
 */
public class BookingRecyclerAdapter extends RecyclerView.Adapter<BookingListViewHolder> {

    List<Booking> bookingList;
    Context context;

    public BookingRecyclerAdapter(Context context, List<Booking> bookingList) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @Override
    public BookingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking_list_row, null);
        return new BookingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingListViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.textView.setText(booking.getDateBooked());
        holder.textView.setOnClickListener(clickListener);
        holder.textView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return (null != bookingList ? bookingList.size() : 0);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BookingListViewHolder holder = (BookingListViewHolder) view.getTag();
            int position = holder.getAdapterPosition();

            Booking booking = bookingList.get(position);
            Toast.makeText(context, "Cost: " + booking.getCost() + "$ Number of Passengers: " + booking.getNumberOfPassengers(), Toast.LENGTH_SHORT).show();
        }
    };
}
