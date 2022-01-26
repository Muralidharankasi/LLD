import Factory.api.ProductController;
import Factory.api.core.TenetVendingMachine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ProductController productController = new ProductController();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            startMachine(productController);
            productController.displayItems();
            while (true) {
                try {
                    productController.displayItems();
                    String productID = bufferedReader.readLine();
                    int units = Integer.parseInt(bufferedReader.readLine());
                    String[] coinStrings = bufferedReader.readLine().split(" ");
                    HashMap<Integer, Integer> hashMap = new HashMap<>();
                    for (String coin : coinStrings) {
                        hashMap.put(Integer.parseInt(coin), hashMap.getOrDefault(Integer.parseInt(coin), 0) + 1);
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(productID, units);
                    map.put("coins", hashMap);
                    System.out.println(productController.purchase(map));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        productController.displayCoinBox();
    }

    private static void startMachine(TenetVendingMachine productController) {

        HashMap<String, Integer> productPrice = new HashMap<>();
        productPrice.put("coke", 15);
        productPrice.put("pepsi", 20);
        productPrice.put("sprite", 25);
        productPrice.put("7up", 5);
        productController.loadPrices(productPrice);
        HashMap<String, Integer> productsCount = new HashMap<>();
        productsCount.put("coke", 12);
        productsCount.put("pepsi", 11);
        productsCount.put("sprite", 25);
        productsCount.put("7up", 7);
        productController.loadProducts(productsCount);
        HashMap<Integer, Integer> coins = new HashMap<>();
        coins.put(10, 10);
        coins.put(5, 50);
        coins.put(1, 20);
        productController.loadCoins(coins);
    }
}
