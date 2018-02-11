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
import com.google.common.base.Joiner;
import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

public class DirectionGoogleMap extends AbstractGoogleMap {
    public static String[] GetDirection(String[] args, String[] waypoints) throws IOException, JSONException {
        String baseUrl = "http://maps.googleapis.com/maps/api/directions/json";// путь к Geocoding API по
        // HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        params.put("language", "ru");// язык данные на котором мы хочем получить
        params.put("mode", "driving");// способ перемещения, может быть driving, walking, bicycling
        params.put("origin", args[0]);// адрес или текстовое значение широты и
        // отправного пункта маршрута
        params.put("destination", args[1]);// адрес или текстовое значение широты и долготы        
        // долготы конечного пункта маршрута
        // добавляем промежуточные точки
        params.put("waypoints", Joiner.on('|').join(waypoints));
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о кординатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("routes").getJSONObject(0);
        location = location.getJSONArray("legs").getJSONObject(0);
        String distance = location.getJSONObject("distance").getString("text");
        String duration = location.getJSONObject("duration").getString("text");
        return (new String[] {distance,duration});
    }
}