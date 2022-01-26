package Factory.api;

import java.util.HashMap;

public class ProductController extends CoinController {

    private boolean isProductExists(String productID) {
        return PRODUCT_FACTORY.containsKey(productID);
    }

    private boolean isUnitsAvailable(String productID, int units) {
        if (!isProductExists(productID)) {
            throw new RuntimeException("The selected product Unavailable");
        }
        return PRODUCT_FACTORY.get(productID) >= units;
    }

    private void isSufficientInventory(String productID, int units) {
        if (!isUnitsAvailable(productID, units)) {
            throw new RuntimeException("Selected Quantity unavailable");
        }
    }

    private void updateProductsInventory(String productID, int units) {
        int newAvailQuant = PRODUCT_FACTORY.remove(productID) - units;
        if (newAvailQuant > 0) {
            PRODUCT_FACTORY.put(productID, newAvailQuant);
        }
    }

    private Integer preCheckRemainingAmount(String productID, int units, int userAmount, HashMap<Integer, Integer> userCoins) {
        isSufficientInventory(productID, units);
        return getRemainingAmount(productID, userAmount, units, userCoins);
    }

    public HashMap purchase(HashMap<String, Object> userInout) {
        HashMap<Integer, Integer> userCoins = (HashMap<Integer, Integer>) userInout.remove("coins");
        String productID = userInout.keySet().iterator().next();
        Integer units = (Integer) userInout.get(productID);
        int userGaveAmount = computeUserGaveAmount(userCoins);
        int remainder = preCheckRemainingAmount(productID, units, userGaveAmount, userCoins);
        updateProductsInventory(productID, units);
        HashMap<Integer, Integer> reminderCoins = new HashMap<>();
        addNewCoins(userCoins);
        if (remainder > 0) {
            reminderCoins = getRemainingCoins(remainder);
        }
        HashMap responseMap = new HashMap();
        responseMap.put("ProductID", productID);
        responseMap.put("units", units);
        responseMap.put("remainders", reminderCoins);
        return responseMap;
    }
}
