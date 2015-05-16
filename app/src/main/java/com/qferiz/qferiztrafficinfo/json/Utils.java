package com.qferiz.qferiztrafficinfo.json;

import org.json.JSONObject;

/**
 * Created by Qferiz on 24/04/2015.
 */
public class Utils {
    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
