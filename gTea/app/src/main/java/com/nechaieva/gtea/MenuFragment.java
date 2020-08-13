package com.nechaieva.gtea;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuFragment extends Fragment {

    String[] data;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void initArray(Context context) {
        data = context.getResources().getStringArray(R.array.tea_menu);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView menuRecyclerView = (RecyclerView) view.findViewById(R.id.menu_items);

        menuRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecyclerView.setLayoutManager(layoutManager);

        initArray(getContext());

        final MenuAdapter mAdapter = new MenuAdapter(data);
        menuRecyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Only supports choosing one item from the menu;
                // however, modifying it to letting choose several items should be
                // more or less trivial.
                // Note: the selected items aren't properly highlighted.
                // Currently not looked into because that's design and not the main functionality.
                int chosen_item = mAdapter.getSelectedPos();
                if (chosen_item == RecyclerView.NO_POSITION) {
                    Toast toast = Toast.makeText(getContext(), "No item chosen", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    navigateToOrder(data[chosen_item]);
                }
            }
        });
    }

    void navigateToOrder(String item) {
        Bundle bundle = new Bundle();
        bundle.putString("order", item);
        NavHostFragment.findNavController(MenuFragment.this)
                .navigate(R.id.action_makeOrder, bundle);
    }
}
