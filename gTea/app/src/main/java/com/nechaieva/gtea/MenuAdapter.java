package com.nechaieva.gtea;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private String[] data;
    private int selectedPos = RecyclerView.NO_POSITION;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView view;

        public ViewHolder(View v) {
            super(v);
            view = v.findViewById(R.id.textView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Redraw the old selection and the new
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
            Log.i("RecycleView position", String.valueOf(selectedPos));
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
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.view.setText(data[position]);
        holder.itemView.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public int getSelectedPos() {
        return selectedPos;
    }
}
