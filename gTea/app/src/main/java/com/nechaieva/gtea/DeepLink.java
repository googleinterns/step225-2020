package com.nechaieva.gtea;

import android.content.Intent;

public enum DeepLink {
    ORDER("/order");

    public final String label;
    public static final String ORDER_ITEM = "item";
    public static final String ORDER_BUNDLE_TAG = "order";
    public static final String EXPECTED_ACTION = Intent.ACTION_VIEW;

    DeepLink(String label) {
        this.label = label;
    }
}
