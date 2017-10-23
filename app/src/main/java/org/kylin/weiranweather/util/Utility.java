package org.kylin.weiranweather.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kylin.weiranweather.db.City;
import org.kylin.weiranweather.db.County;
import org.kylin.weiranweather.db.Province;

/**
 * Created by hello on 2017/10/22.
 */

public class Utility {
    private static final String TAG = "Utility";
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces =new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject provincesObject =allProvinces.getJSONObject(i);
                    Province province =new Province();
                    province.setProvinceName(provincesObject.getString("name"));
                    province.setProvinceCode(provincesObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId){
        Log.d(TAG, "handleCityResponse: "+response +"\n provinceId: "+provinceId);
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities =new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    JSONObject cityObject =allCities.getJSONObject(i);
                    City city =new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return  true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties =new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    JSONObject countyObject =allCounties.getJSONObject(i);
                    County county =new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
