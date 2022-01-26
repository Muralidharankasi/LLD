package Factory.api.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public interface TenetVendingMachine {
    final TreeMap<String, Integer> PRODUCT_FACTORY = new TreeMap<>();
    final TreeMap<String, Integer> PRICE_FACTORY = new TreeMap<>();
    final TreeMap<Integer, Integer> COIN_FACTORY = new TreeMap<>(Collections.reverseOrder());

    public void loadProducts(HashMap<String, Integer> newProducts);

    public void loadPrices(HashMap<String, Integer> productsToPrice);

    public void loadCoins(HashMap<Integer, Integer> coinFact);

    public void displayItems();
}
