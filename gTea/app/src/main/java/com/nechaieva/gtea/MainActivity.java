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

    // This bundle exists to pass information from the Intent to the
    // OrderFragment. Since Intent is received by an Activity,
    // and MenuFragment -> OrderFragment navigation link is where
    // the order gets processed, we currently save the information in the Activity
    // to be accessed from the MenuFragment later using the getter.
    // However, a better way to do it would be to explicitly specify the deep link
    // in the fragment navigation map.
    private Bundle startingInfo = new Bundle();
    final String expectedIntentAction = DeepLink.EXPECTED_ACTION;
    public static final String ORDER_TAG = "order";
    final String MAIN_ACTIVITY_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Log.i(ORDER_TAG, "onCreate() called");
        if (Objects.nonNull(intent)) {
            handleIntent(intent);
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        // Should catch the intent if the app is already launched.
        // However, I'm not sure how the Test Tool behaves in this case;
        // seemingly it re-launches the app every time it's run.
        super.onNewIntent(intent);
        if (Objects.nonNull(intent)) {
            handleIntent(intent);
        }
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
            Log.i(MAIN_ACTIVITY_TAG, "popping backstack");
            fm.popBackStack();
        } else {
            Log.i(MAIN_ACTIVITY_TAG, "nothing on backstack, calling super");
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

        String action = intent.getAction();
        Uri data = intent.getData();
        logIntent(intent, action, expectedIntentAction);

        if (expectedIntentAction.equals(action) && data != null) {
            handleDeepLink(data);
        }
    }

    void logIntent(Intent intent, String action, String expectedAction) {
        Log.i(ORDER_TAG, "Intent handling called");
        Log.i(ORDER_TAG, "Intent: " + intent.toString());
        Log.i(ORDER_TAG, "Action: " + action);
        Log.i(ORDER_TAG, "Expected action: " + expectedAction);
        Log.i(ORDER_TAG, "Data: " + intent.getData());
    }

    private void handleDeepLink(Uri data) {
        Log.i(ORDER_TAG, "handleDeepLink() called");
        if (Objects.equals(data.getPath(), DeepLink.ORDER.label)) {
            String item = data.getQueryParameter(DeepLink.ORDER_ITEM);
            loadOrderInfoToBundle(item);
        }
    }

    private void loadOrderInfoToBundle(String item) {
        Log.i(ORDER_TAG, "loadOrder() called");
        startingInfo.putString(DeepLink.ORDER_BUNDLE_TAG, item);
    }

    public Bundle getStartingInfo() {
        return startingInfo;
    }
}
