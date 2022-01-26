package Factory.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoinController extends FactoryCore {

    protected Integer getCalcCostForProduct(String productID, Integer noUnits) {
        return super.PRICE_FACTORY.get(productID) * noUnits;
    }

    protected Integer getCostForProduct(String productID) {
        return PRICE_FACTORY.get(productID);
    }


    protected int computeUserGaveAmount(HashMap<Integer, Integer> userCoins) {
        int userGaveAmount = 0;
        for (Map.Entry<Integer, Integer> entry : userCoins.entrySet()) {
            userGaveAmount += entry.getKey() * entry.getValue();
        }
        return userGaveAmount;
    }

    protected Integer computeChargeableAmount(String productID, int userAmount, int units) {
        int calcAmount = getCalcCostForProduct(productID, units);
        if (calcAmount > userAmount) {
            throw new RuntimeException("Given amount insufficient to process the product purchase");
        }
        return userAmount - calcAmount;
    }

    protected Integer getRemainingAmount(String productID, int userAmount, int units, HashMap<Integer, Integer> userCoins) {
        int k = computeChargeableAmount(productID, userAmount, units);
        if (k > 0) {
            checkRefundable(k, userCoins);
        }
        return k;
    }

    protected void checkRefundable(int k, HashMap<Integer, Integer> userCoins) {
        for (Map.Entry<Integer, Integer> entry : COIN_FACTORY.entrySet()) {
            int coin = entry.getKey();
            int count = entry.getValue();
            while (count > 0 && k > 0) {
                if (k < coin) break;
                k -= coin;
                --count;
            }
            if (k == 0) return;

        }
        throw new RuntimeException("No Change");
    }

    protected HashMap<Integer, Integer> getRemainingCoins(int remainder) {
        HashMap<Integer, Integer> remainderCoins = new HashMap<>();
        Set<Integer> coins = COIN_FACTORY.keySet();
        for (int coin : coins) {
            int actualCoinAmount = COIN_FACTORY.get(coin);
            int count = COIN_FACTORY.get(coin);
            while (count > 0 && remainder > 0) {
                if (remainder < coin) break;
                remainder -= coin;
                --count;
            }
            COIN_FACTORY.put(coin, count);
            if (actualCoinAmount - count > 0)
                remainderCoins.put(coin, actualCoinAmount - count);
            if (remainder == 0) break;
        }
        return remainderCoins;
    }

    protected void addNewCoins(HashMap<Integer, Integer> userCoins) {
        for (Map.Entry<Integer, Integer> entry : userCoins.entrySet()) {
            COIN_FACTORY.put(entry.getKey(), COIN_FACTORY.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

}
