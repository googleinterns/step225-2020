package com.nechaieva.gtea;

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

    String[] data = {"Black tea", "Green tea"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.menu_items);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final MenuAdapter mAdapter = new MenuAdapter(data);
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int chosen_item = mAdapter.getSelectedPos();
                if (chosen_item == RecyclerView.NO_POSITION) {
                    Toast toast = Toast.makeText(getContext(), "No item chosen", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("order", data[chosen_item]);
                NavHostFragment.findNavController(MenuFragment.this)
                        .navigate(R.id.action_makeOrder, bundle);
            }
        });
    }
}
