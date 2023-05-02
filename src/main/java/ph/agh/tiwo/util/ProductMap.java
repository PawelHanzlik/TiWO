package ph.agh.tiwo.util;

import java.util.HashMap;
import java.util.Map;

public class ProductMap {

    private static final Map<String, String> urlMap;
    private static final Map<String, Double> costMap;
    // Instantiating the static map
    static
    {
        urlMap = new HashMap<>();
        urlMap.put("drukarka", "https://pwcbh.blob.core.windows.net/products/drukarka.jpg");
        urlMap.put("wiertarka", "https://pwcbh.blob.core.windows.net/products/wiertarka.jpg");
        urlMap.put("chleb", "https://pwcbh.blob.core.windows.net/products/chleb.jpg");
        urlMap.put("maslo", "https://pwcbh.blob.core.windows.net/products/maslo.jpg");
        urlMap.put("ser", "https://pwcbh.blob.core.windows.net/products/ser.jpg");
        urlMap.put("szynka", "https://pwcbh.blob.core.windows.net/products/szynka.jpg");
        urlMap.put("mleko", "https://pwcbh.blob.core.windows.net/products/mleko.jpg");
        urlMap.put("ciastka", "https://pwcbh.blob.core.windows.net/products/ciastka.jpg");
        urlMap.put("cukier", "https://pwcbh.blob.core.windows.net/products/cukier.jpg");

        costMap = new HashMap<>();
        costMap.put("drukarka", 350.0);
        costMap.put("wiertarka", 250.0);
        costMap.put("chleb", 8.0);
        costMap.put("maslo", 6.50);
        costMap.put("ser", 35.0);
        costMap.put("szynka", 40.0);
        costMap.put("mleko", 3.50);
        costMap.put("ciastka", 12.0);
        costMap.put("cukier", 5.0);
    }

    public static String getUrl(String productName){
        return urlMap.get(productName);
    } // getOrDefault
    public static Double getCost(String productName){
        return costMap.getOrDefault(productName, 10.0);
    }
}
