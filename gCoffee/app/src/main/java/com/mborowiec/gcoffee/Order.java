package com.mborowiec.gcoffee;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Order extends AppCompatActivity implements AdapterView.OnItemClickListener, OrderMatch{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ListView listView = findViewById(R.id.coffee_listView);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,  R.array.coffee_list, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.order);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.order:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Check whether the activity was launched via a deeplink
        if (Objects.equals(getIntent().getAction(), Intent.ACTION_VIEW)) {
            Uri uri = getIntent().getData();
            if (uri !=  null) handleDeepLinks(uri);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String[] coffeeList = this.getResources().getStringArray(R.array.coffee_list);
        String coffee = coffeeList[position];

        // Ask the user to confirm order of the coffee type they clicked on
        showConfirmationDialog(coffee);
    }

    /**
     * Asks user to confirm their order.
     * @param coffee type of coffee the user is ordering
     */
    private void showConfirmationDialog(final String coffee) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getString(R.string.confirmation, coffee));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        makeOrder(coffee);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder1.create();
        alert.show();
    }

    /**
     * Displays order confirmation.
     * @param coffee type of ordered  coffee
     */
    private void makeOrder(String coffee) {
        Toast.makeText(this, getString(R.string.ordered, coffee),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks whether the menu contains the ordered item.
     * @param item  item ordered by user
     * @return boolean value
     */
    public boolean menuContainsItem(String item, String[] coffeeList) {
        if (item == null) return false;
        else {
            item = item.toLowerCase();

            Stream<String> coffeeStream = Stream.of(coffeeList).map(String::toLowerCase);
            List<String> menu = coffeeStream.collect(Collectors.toList());

            return menu.contains(item);
        }
    }

    /**
     * Handles the incoming deeplink with ordered item.
     * Checks whether the item is on the menu.
     * If it is, asks to confirm order, if it is not, notifies the user about it.
     * If the input is null, it  starts the Main activity instead of proceeding to order.
     * @param data incoming deeplink
     */
    private void handleDeepLinks(Uri data) {
        String coffee = data.getQueryParameter("inMenuName");
        if (coffee == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        String[] coffeeList = this.getResources().getStringArray(R.array.coffee_list);
        String menuMatch = findMatch(coffee, coffeeList);

        if (menuMatch == null) {
            Toast.makeText(this, getString(R.string.not_on_menu, coffee),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            showConfirmationDialog(menuMatch);
        }
    }

    /**
     * Looks for the closest match (with closest distance) in the menu.
     * @param order an input with potential typos
     * @return the closest order match
     */
    public String findMatch(String order, String[] menu) {
        //TODO: set treshold for Levenshtein distance?

        if (order == null) {
            return null;
        }

        StringUtil util = new StringUtil();
        String normalizedOrder = util.normalize(order);

        // Return null if the string is empty after normalizing
        if (normalizedOrder.equals("")) {
            return null;
        }

        if (menuContainsItem(normalizedOrder, menu)) {
           return normalizedOrder;
        }
        else {
            int[] distances = new int[menu.length];

            // Check Levenshtein distances between the order and menu items
            for (int i = 0; i < menu.length; i++) {
                String normalizedMenuItem = util.normalize(menu[i]);
                int distance = util.calculateLevenshteinDistance(normalizedOrder,normalizedMenuItem);
                distances[i] = distance;
            }

            // Find the item with the smallest distance (most likely to be a match)
            int indexMin = 0;
            int min = distances[indexMin];

            for (int i = 1; i < distances.length; i++){
                if (distances[i] < min) {
                    min = distances[i];
                    indexMin = i;
                }
            }
            // Return the menu item with smallest distance
            return menu[indexMin];
        }
    }

    //TODO: removing "fluff" from the input
}


