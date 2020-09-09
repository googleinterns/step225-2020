package com.nechaieva.gtea.utils;

import java.util.Optional;

/**
 * Given a query that represent the user's order,
 * tries to determine the matching menu item so that it can be passed back to the handler.
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
     * Given a text query passed from the user, return the string that represent
     * an existing menu item, or empty if there is no fitting candidate for the criteria we use.
     * @param query
     * A user query.
     * @return
     * A menu item.
     */
    Optional<String> findInMenu(String query);
}
