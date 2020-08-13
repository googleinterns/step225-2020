package com.nechaieva.gtea;

import android.content.Intent;

public enum DeepLink {
    ORDER("/order");

    public final String label;
    public static final String orderItem = "item";
    public static final String expectedAction = Intent.ACTION_VIEW;

    DeepLink(String label) {
        this.label = label;
    }
}
