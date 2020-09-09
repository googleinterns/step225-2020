package com.nechaieva.gtea.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nechaieva.gtea.DeepLink;
import com.nechaieva.gtea.R;

public class OrderFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView show_order = view.findViewById(R.id.text_view_order);
        assert getArguments() != null;
        String order = getArguments().getString(DeepLink.ORDER_BUNDLE_TAG);
        show_order.setText(getString(R.string.order_text, order));

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("navigation", NavHostFragment.findNavController(OrderFragment.this).toString());
                NavHostFragment.findNavController(OrderFragment.this)
                        .navigate(R.id.action_backToMenu);
                Log.i("navigation", "Back func called");
            }
        });
    }

}
