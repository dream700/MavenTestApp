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
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

public class DistanceGoogleMap extends AbstractGoogleMap {

    private static final double EARTH_RADIUS = 6371.; // Радиус Земли

    public static double GetDistance(final String[] args) throws IOException, JSONException {
        Point subwayStationPoint = getPoint(args[0]);
        Point addressPoint = getPoint(args[1]);

        // Рассчитываем расстояние между точками
        double dlng = deg2rad(subwayStationPoint.lng - addressPoint.lng);
        double dlat = deg2rad(subwayStationPoint.lat - addressPoint.lat);
        double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(addressPoint.lat))
                * cos(deg2rad(subwayStationPoint.lat)) * sin(dlng / 2) * sin(dlng / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return c;
    }

    /**
     * Класс точки, хранит зачения в градусах
     *
     */
    private static class Point {

        public double lat;
        public double lng;

        public Point(final double lng, final double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        @Override
        public String toString() {
            return lat + "," + lng;
        }
    }

    /**
     * Геокодирует адрес
     *
     * @param address
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private static Point getPoint(final String address) throws IOException, JSONException {
        String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        // местоположения
        params.put("address", address);// адрес, который нужно геокодировать
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о кординатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        double lng = location.getDouble("lng");// долгота
        double lat = location.getDouble("lat");// широта
        Point point = new Point(lng, lat);
        return point;
    }

    /**
     * Преобразует значение из градусов в радианы
     *
     * @param degree
     * @return
     */
    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }
}
