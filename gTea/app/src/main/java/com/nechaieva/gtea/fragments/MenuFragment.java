package com.nechaieva.gtea.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nechaieva.gtea.DeepLink;
import com.nechaieva.gtea.MainActivity;
import com.nechaieva.gtea.MenuAdapter;
import com.nechaieva.gtea.R;
import com.nechaieva.gtea.utils.LevenshteinDistCalculator;
import com.nechaieva.gtea.utils.LevenshteinOrderProcessor;
import com.nechaieva.gtea.utils.OrderProcessor;

import java.util.Optional;

public class MenuFragment extends Fragment {

    String[] data;
    static boolean orderChecked = false;
    final String ORDER_TAG = MainActivity.ORDER_TAG;
    final String EXCEPTION_TAG = "Exception";
    MenuAdapter mAdapter;
    OrderProcessor orderProcessor;

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

        final RecyclerView menuRecyclerView = view.findViewById(R.id.menu_items);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecyclerView.setLayoutManager(layoutManager);

        initArray(requireContext());

        mAdapter = new MenuAdapter(data);
        orderProcessor = new LevenshteinOrderProcessor(data);

        menuRecyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseItem();
            }
        });

        checkVoiceOrder();
    }

    void chooseItem() {
        // Only supports choosing one item from the menu;
        // however, modifying it to letting choose several items should be
        // more or less trivial.
        // Note: the selected items aren't properly highlighted.
        // Currently not looked into because that's design and not the main functionality.
        int chosen_item = mAdapter.getSelectedPos();

        if (chosen_item == RecyclerView.NO_POSITION) {
            Toast toast = Toast.makeText(getContext(), "No item chosen",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            try {
                navigateToOrder(data[chosen_item]);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                Log.i(EXCEPTION_TAG,
                        "Chosen item index is out of range for the item list");
                        /*
                        If we are here, something went very wrong, since the data list is the
                        very same list we use to create the adapter, from which we receive the
                        index of the selected item, and data[] is only expected to be modified
                        during the initialization, as soon as we receive the context.
                         */
            }
        }
    }

    void checkVoiceOrder() {
        /*
        If an Intent was received by MainActivity, but there was one explicit way
        to handle it yet at that point, this is where the intent gets processed.
         */
        if (orderChecked) {
            return;
        } else {
            orderChecked = true;
        }
        MainActivity mainActivity = (MainActivity)this.getActivity();
        if (mainActivity == null) {
            Log.e(ORDER_TAG, "No MainActivity found");
            return;
        }

        Bundle order = mainActivity.getStartingInfo();
        Optional<String> menuItem = checkInMenu(order);
        boolean navigateTo = menuItem.isPresent();

        final String messageIfFalse = "Order is null or empty";

        if (navigateTo) {
            order.putString(DeepLink.ORDER_BUNDLE_TAG, menuItem.get());
            navigateToOrder(order);
        }
        logVoiceOrder(order, navigateTo, messageIfFalse);
    }

    void logVoiceOrder(Bundle order, boolean navigatedTo, String messageIfFalse) {
        Log.i(ORDER_TAG, "checkVoiceOrder()");
        if (navigatedTo) {
            Log.i(ORDER_TAG, "Bundle:" + order.toString());
        } else {
            Log.i(ORDER_TAG, "Bundle was not used: " + messageIfFalse);
        }
    }

    void navigateToOrder(String item) {
        Bundle bundle = new Bundle();
        bundle.putString(DeepLink.ORDER_BUNDLE_TAG, item);
        navigateToOrder(bundle);
    }

    void navigateToOrder(Bundle bundle) {
        NavHostFragment.findNavController(MenuFragment.this)
                .navigate(R.id.action_makeOrder, bundle);
    }

    Optional<String> checkInMenu(Bundle order) {
        if (order == null || order.isEmpty() || order.getString(DeepLink.ORDER_BUNDLE_TAG) == null) {
            return Optional.empty();
        }
        return findMenuItem(order.getString(DeepLink.ORDER_BUNDLE_TAG));
    }

    Optional<String> findMenuItem(String query) {
        return orderProcessor.findInMenu(query);
    }
}
