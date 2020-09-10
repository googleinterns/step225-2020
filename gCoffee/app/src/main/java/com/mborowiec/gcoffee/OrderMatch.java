package com.mborowiec.gcoffee;

public interface OrderMatch {

    public boolean menuContainsItem(String item, String[] menu);

    public String findMatch(String order, String[] menu);
}
