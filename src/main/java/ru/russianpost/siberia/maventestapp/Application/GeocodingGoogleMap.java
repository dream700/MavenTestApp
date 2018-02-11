/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

/**
 *
 * @author andy
 */
import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

public class GeocodingGoogleMap extends AbstractGoogleMap {
    
    public static double[] GetGeocoding(String address) throws IOException, JSONException {
        String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        params.put("address", address);// адрес, который нужно геокодировать
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о кординатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        double lng = location.getDouble("lng");// долгота
        double lat = location.getDouble("lat");// широта
        return ( new double[]{lat,lng});
    }
}