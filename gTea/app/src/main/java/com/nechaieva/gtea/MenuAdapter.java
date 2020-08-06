package com.nechaieva.gtea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private String[] data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view;

        public ViewHolder(View v) {
            super(v);
            view = v.findViewById(R.id.textView);
        }
    }

    public MenuAdapter(String[] data) {
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        //TextView v = (TextView) rootView.findViewById(R.id.textView);
        //...
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view.setText(data[position]);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
