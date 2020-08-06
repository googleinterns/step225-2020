package com.mborowiec.gcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Order extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
        builder1.setMessage("Do you want to order " + coffee);
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
        Toast.makeText(this, "Ordered: " + coffee,
                Toast.LENGTH_SHORT).show();
    }
}
