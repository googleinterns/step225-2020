package com.nechaieva.gtea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // This is a dirty hack.
    Bundle startingInfo = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Log.i("order", "onCreate() called");
        if (Objects.nonNull(intent)) handleIntent(intent);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        // Should catch the intent if the app is already launched.
        // However, I'm not sure how the Test Tool behaves in this case.
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_open_user_profile:
                NavHostFragment.findNavController(getVisibleFragment())
                        .navigate(R.id.AccountFragment);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    public Fragment getVisibleFragment() {
        // It is assumed that the first visible fragment that we find
        // is that last one that is opened, since the fragment stack might
        // not work in this case:
        // the fragment stack initiates as empty.
        // It works for the case when we simply navigate between fragments:
        // then the account fragment, for which this part is made of,
        // simply works as part of the navigation, just specified in code.
        // Still, better long-term solution would be to somehow add it to
        // nav_graph or update the back stack manually.
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    void handleIntent(Intent intent) {
        Log.i("order", "handleIntent() called");
        Log.i("order", "Intent: " + intent.toString());
        String action = intent.getAction();
        String expectedAction = DeepLink.expectedAction;
        Log.i("order", "Action: " + action);
        Log.i("order", "Expected action: " + expectedAction);
        Uri data = intent.getData();
        Log.i("order", "Data: " + data);
        if (expectedAction.equals(action) && data != null) {
            handleDeepLink(data);
        }
    }

    private void handleDeepLink(Uri data) {
        Log.i("order", "handleDeepLink() called");
        if (Objects.equals(data.getPath(), DeepLink.ORDER.label)) {
            String item = data.getQueryParameter(DeepLink.orderItem);
            loadOrderInfoToBundle(item);
        }
    }

    private void loadOrderInfoToBundle(String item) {
        Log.i("order", "loadOrder() called");
        startingInfo.putString("order", item);
    }

    public Bundle getStartingInfo() {
        return startingInfo;
    }
}
