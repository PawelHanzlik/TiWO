package ph.agh.tiwo.util;

import java.util.HashMap;
import java.util.Map;

public class UrlMap {

    private static final Map<String, String> map;

    // Instantiating the static map
    static
    {
        map = new HashMap<>();
        map.put("drukarka", "https://pwcbh.blob.core.windows.net/products/drukarka.jpg");
        map.put("wiertarka", "https://pwcbh.blob.core.windows.net/products/wiertarka.jpg");
        map.put("chleb", "https://pwcbh.blob.core.windows.net/products/chleb.jpg");
        map.put("maslo", "https://pwcbh.blob.core.windows.net/products/maslo.jpg");
        map.put("ser", "https://pwcbh.blob.core.windows.net/products/ser.jpg");
        map.put("szynka", "https://pwcbh.blob.core.windows.net/products/szynka.jpg");
        map.put("mleko", "https://pwcbh.blob.core.windows.net/products/mleko.jpg");
        map.put("ciastka", "https://pwcbh.blob.core.windows.net/products/ciastka.jpg");
        map.put("cukier", "https://pwcbh.blob.core.windows.net/products/cukier.jpg");
    }

    public static String getUrl(String productName){
        return map.get(productName);
    } // getOrDefault
}
