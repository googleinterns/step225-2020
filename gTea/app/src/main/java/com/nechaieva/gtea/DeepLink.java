package com.nechaieva.gtea;

public enum DeepLink {
    ORDER("/order");

    public final String label;
    public static final String orderItem = "item";

    DeepLink(String label) {
        this.label = label;
    }
}
