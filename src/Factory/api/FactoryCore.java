package Factory.api;

import Factory.api.core.TenetVendingMachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FactoryCore implements TenetVendingMachine {

    @Override
    public void loadProducts(HashMap<String, Integer> newProducts) {
        if (!PRICE_FACTORY.keySet().containsAll(newProducts.keySet())) {
            throw new RuntimeException("Few products price is not added. Kindly add them before updating inventory");
        }
        for (Map.Entry<String, Integer> entry : newProducts.entrySet()) {
            this.PRODUCT_FACTORY.put(entry.getKey(), this.PRODUCT_FACTORY.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    @Override
    public void loadPrices(HashMap<String, Integer> productsToPrice) {
        this.PRICE_FACTORY.putAll(productsToPrice);
    }

    @Override
    public void loadCoins(HashMap<Integer, Integer> coinFact) {
        this.COIN_FACTORY.putAll(coinFact);
    }

    @Override
    public void displayItems(){
        System.out.println("Factory Display :: ........");
        System.out.println("Available products  "+PRODUCT_FACTORY);
        System.out.println("prices "+PRICE_FACTORY);
    }

    public void displayCoinBox(){
        System.out.println("Available coins "+COIN_FACTORY);
    }

}
