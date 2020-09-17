package com.nechaieva.gtea.utils;

import java.util.Optional;

/**
 * Given a query that represent the user's order,
 * tries to determine the menu item that matches the order.
 * Can be precise or approximate (looking for the closest query).
 */
public interface OrderProcessor {

    /**
     * Load the menu used for processing.
     * Replaces the previous one if a menu was already loaded.
     * @param menu
     * A list of menu items that the user might order.
     */
    void loadMenu(String[] menu);

    /**
     * Given a text query passed from the user,
     * returns a menu element that matches the query -
     * a string from the menu that is the closest one to the query passed to the function.
     * Wraps it in Optional.
     * Returns an empty Optional if there is no candidate falling under the criteria we use.
     * @param query
     * A user query.
     * @return
     * A menu item.
     */
    Optional<String> findInMenu(String query);
}
