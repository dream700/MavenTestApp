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
import java.util.Comparator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.util.Iterator;

public class DistanceMatrixGoogleMap extends AbstractGoogleMap {

    public static String[] GetDistanceMatrix(String[] origins, String[] waypoints, String[] destionations) throws IOException, JSONException {
        String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json";// путь к Geocoding API по HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        params.put("language", "ru");// язык данных
        params.put("mode", "driving");// идем пешком, может быть driving, walking, bicycling
        // адрес или координаты отправных пунктов
        params.put("origins", Joiner.on('|').join(origins));
        // добавляем промежуточные точки
        params.put("waypoints", Joiner.on('|').join(waypoints));
        // адрес или координаты пунктов назначения
        // в запросе адреса должны раделяться символом '|'
        params.put("destinations", Joiner.on('|').join(destionations));
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        JSONObject location = response.getJSONArray("rows").getJSONObject(0);
        JSONArray arrays = location.getJSONArray("elements");// Здесь лежат все рассчитаные значения
        // Ищем путь на который мы потратим минимум времени
        JSONObject result = Ordering.from(new Comparator<JSONObject>() {
            @Override
            public int compare(final JSONObject o1, final JSONObject o2) {
                Integer duration1 = getDurationValue(o1);
                Integer duration2 = getDurationValue(o2);
                return duration1.compareTo(duration2);// Сравниваем по времени в пути
            }

            /**
             * Возвращает время в пути
             *
             * @param obj
             * @return
             */
            private int getDurationValue(JSONObject obj) {
                try {
                    return obj.getJSONObject("duration").getInt("value");
                } catch (final JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).min(new Iterable<JSONObject>() {
            @Override
            public Iterator<JSONObject> iterator() {
                Iterator<JSONObject> iterator = new Iterator<JSONObject>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return (index < arrays.length());
                    }

                    @Override
                    public JSONObject next() {
                        try {
                            JSONObject result;
                            if (index < arrays.length()) {
                                String destionation = destionations[index];
                                result = arrays.getJSONObject(index++);
                                result.put("address", destionation);// Добавим сразу в структуру и адрес, потому как его нет в
                                // этом рассчете
                            } else {
                                result = null;
                            }
                            return result;
                        } catch (final JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                return iterator;
            }
        }
        );
//                
//                
//                .min(new AbstractIterator<JSONObject>() {// К сожалению JSONArray нельзя итереровать, по этому обернем его
//            private int index = 0;
//
//            @Override
//            protected JSONObject computeNext() {
//                try {
//                    JSONObject result;
//                    if (index < arrays.length()) {
//                        final String destionation = destionations[index];
//                        result = arrays.getJSONObject(index++);
//                        result.put("address", destionation);// Добавим сразу в структуру и адрес, потому как его нет в
//                        // этом рассчете
//                    } else {
//                        result = endOfData();
//                    }
//                    return result;
//                } catch (final JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        String distance = result.getJSONObject("distance").getString("text");// расстояние в километрах
        String duration = result.getJSONObject("duration").getString("text");// время в пути
        String address = result.getString("address");// адрес
        return (new String[]{address, distance, duration
        });
    }

    public static void main(final String[] args) throws IOException, JSONException {
        final String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        params.put("language", "ru");// язык данных
        params.put("mode", "walking");// идем пешком, может быть driving, walking, bicycling
        // адрес или координаты отправных пунктов
        final String[] origins = {"Россия, Москва, улица Поклонная, 12"};
        params.put("origins", Joiner.on('|').join(origins));
        // адрес или координаты пунктов назначения
        final String[] destionations = { //
            "Россия, Москва, станция метро Парк Победы", //
            "Россия, Москва, станция метро Кутузовская" //
        };
        // в запросе адреса должны раделяться символом '|'
        params.put("destinations", Joiner.on('|').join(destionations));
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url); // Можем проверить что вернет этот путь в браузере
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        final JSONObject location = response.getJSONArray("rows").getJSONObject(0);
        final JSONArray arrays = location.getJSONArray("elements");// Здесь лежат все рассчитаные значения
        // Ищем путь на который мы потратим минимум времени
        final JSONObject result = Ordering.from(new Comparator<JSONObject>() {
            @Override
            public int compare(final JSONObject o1, final JSONObject o2) {
                final Integer duration1 = getDurationValue(o1);
                final Integer duration2 = getDurationValue(o2);
                return duration1.compareTo(duration2);// Сравниваем по времени в пути
            }

            /**
             * Возвращает время в пути
             *
             * @param obj
             * @return
             */
            private int getDurationValue(final JSONObject obj) {
                try {
                    return obj.getJSONObject("duration").getInt("value");
                } catch (final JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).min(new AbstractIterator<JSONObject>() {// К сожалению JSONArray нельзя итереровать, по этому обернем его
            private int index = 0;

            @Override
            protected JSONObject computeNext() {
                try {
                    JSONObject result;
                    if (index < arrays.length()) {
                        final String destionation = destionations[index];
                        result = arrays.getJSONObject(index++);
                        result.put("address", destionation);// Добавим сразу в структуру и адрес, потому как его нет в
                        // этом рассчете
                    } else {
                        result = endOfData();
                    }
                    return result;
                } catch (final JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        final String distance = result.getJSONObject("distance").getString("text");// расстояние в километрах
        final String duration = result.getJSONObject("duration").getString("text");// время в пути
        final String address = result.getString("address");// адрес
        System.out.println(address + "\n" + distance + "\n" + duration);
    }
}
