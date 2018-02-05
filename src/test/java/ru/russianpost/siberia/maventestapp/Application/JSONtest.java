/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.ParseException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andy
 */
public class JSONtest {

    public JSONtest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    public void getArray(Object object2) throws ParseException {
        JSONArray jsonArr = (JSONArray) object2;
        for (int k = 0; k < jsonArr.length(); k++) {
            if (jsonArr.get(k) instanceof JSONObject) {
                parseJson((JSONObject) jsonArr.get(k));
            }
        }
    }

    public void parseJson(JSONObject jsonObject) throws ParseException, JSONException {

        Iterator<?> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String obj = iterator.next().toString();

            if (jsonObject.get(obj) instanceof JSONArray) {
                System.out.println(obj.toString());
                System.out.println("--------------------");
                getArray(jsonObject.get(obj));
            } else {
                if (jsonObject.get(obj) instanceof JSONObject) {
                    parseJson((JSONObject) jsonObject.get(obj));
                } else {
                    System.out.println(obj.toString() + "\t" + jsonObject.get(obj));
//                    if ("po_version".equals(obj.toString())) {
//                        System.out.println("VERSION:" + jsonObject.get(obj));
//                    } else if ("source_id".equals(obj.toString())) {
//                        System.out.println("SOFWARE: " + jsonObject.get(obj));
//                    } else if ("index".equals(obj.toString())) {
//                        System.out.println("Index from:" + jsonObject.get(obj));
//                    } else if ("index_to".equals(obj.toString())) {
//                        System.out.println("Index to:" + jsonObject.get(obj));
//                    } else if ("name".equals(obj.toString())) {
//                        System.out.println("Sender name:" + jsonObject.get(obj));
//                    } else if ("category_id".equals(obj.toString())) {
//                        System.out.println("Category:" + jsonObject.get(obj));
//                    }else if ("type".equals(obj.toString())) {
//                        System.out.println("Type:" + jsonObject.get(obj));
//                    }
                }
            }
        }
    }

    @Test
    public void testJSON() {
        String json = "{\"request_bar_code\":\"63010217290827\","
                + "\"sys_id\":{\"date_time\":\"2018-02-05 14:18:14\",\"source_id\":\"DATA-CLOUD\",\"po_version\":\"1.1.56\"},"
                + "\"rpo_list\":[{"
                + "\"base_info\":{\"id\":\"63010217290827\",\"type\":2,"
                + "\"properties\":[{\"name\":\"rpo_not_type\",\"value\":\"-1\"}],"
                + "\"entity_type\":1,\"category_id\":1,\"message_id\":\"ZTRACKING63010217290827\",\"sender_id\":\"630964\",\"version_number\":\"2.11\","
                + "\"sys_id\":{\"date_time\":\"2017-12-01 08:00:01\",\"source_id\":\"R\",\"po_version\":\"8.22.1\"},"
                + "\"bar_code\":\"63010217290827\",\"create_date_time\":\"2017-12-01 14:54:31\",\"create_time_zone\":\"+07:00\","
                + "\"create_address_data\":{\"type\":3,\"index\":\"630102\",\"border_index\":\"630102\"},"
                + "\"direct_ctg\":1,\"date_processed\":\"2017-12-01 08:00:11\"},"
                + "\"rpo_info\":{\"send_ctg\":2,\"trans_type\":1,\"post_mark\":[0],\"mail_rank\":4,\"trans_ctg\":-1,\"pay_type\":[2],\"index_from\":\"630102\",\"index_to\":\"633270\",\"country_from\":\"643\",\"country_to\":\"643\",\"inter_type\":1,"
                + "\"weight\":[{\"type\":1,\"value\":20.0,\"measurement\":\"g\"}],"
                + "\"money\":[{\"type\":3,\"value\":4100,\"measurement\":\"\"},{\"type\":6,\"value\":985,\"measurement\":\"\"}],"
                + "\"rcpn\":{\"type\":3,\"properties\":[],\"name\":\"Магнушевский Алексей ЕВГЕНЬЕВИЧ\",\"category_id\":1},"
                + "\"rcpn_address\":{\"type\":4,\"index\":\"633270\",\"border_index\":\"633270\","
                + "\"address\":{\"type\":1,\"region\":\"\",\"area\":\"\",\"place\":\"д. Новый Шарап\",\"street\":\"Березовая 27 кв.1\","
                + "\"house\":{\"value\":\"\",\"letter\":\"\",\"corpus\":\"\",\"building\":\"\",\"room\":\"\"}"
                + "},"
                + "\"country\":{\"a2_code\":\"\",\"a3_code\":\"\",\"ru_code\":\"643\"}"
                + "},"
                + "\"sndr\":{\"type\":2,"
                + "\"properties\":[{\"name\":\"inn\",\"value\":\"5405116108\"}],"
                + "\"name\":\"Арбитражный суд\",\"category_id\":2},"
                + "\"sndr_address\":{\"type\":1,\"index\":\"630102\",\"border_index\":\"630102\","
                + "\"address\":{\"type\":1,\"region\":\"НОВОСИБИРСКАЯ ОБЛАСТЬ\",\"area\":\"\",\"place\":\"НОВОСИБИРСК\",\"street\":\"Нижегородская\","
                + "\"house\":{\"value\":\"6\",\"letter\":\"\",\"corpus\":\"\",\"building\":\"\",\"room\":\"\"}"
                + "},"
                + "\"country\":{\"a2_code\":\"\",\"a3_code\":\"\",\"ru_code\":\"643\"}"
                + "}"
                + "}"
                + "}"
                + "],"
                + "\"bag_list\":[],\"document_list\":[],\"links_list\":[]}";
        String http = "http://int.reports.pochta.ru/easops/api/Doc//";
        JSONObject obj = new JSONObject(json);
        try {
            parseJson(obj);
        } catch (ParseException ex) {
            Logger.getLogger(JSONtest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(JSONtest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray links_list = obj.getJSONArray("links_list");
        JSONObject sys_id = obj.getJSONObject("sys_id");
        JSONArray rpo_list = obj.getJSONArray("rpo_list");
        JSONObject base = rpo_list.getJSONObject(0);
        JSONObject base_info = base.getJSONObject("base_info");
        JSONObject sys_id1 = base_info.getJSONObject("sys_id");
        System.out.println("Version:" + sys_id1.getString("po_version") + " " + sys_id1.getString("source_id"));
        JSONObject rpo_info = base.getJSONObject("rpo_info");
        JSONObject rcpn = rpo_info.getJSONObject("rcpn");
        System.out.println("Name:" + rcpn.getString("name"));
        JSONObject rcpn_address = rpo_info.getJSONObject("rcpn_address");
        System.out.println("Recpt index:" + rcpn_address.getString("index"));
        JSONObject raddress = rcpn_address.getJSONObject("address");
        System.out.println("Place:" + raddress.getString("place"));
        System.out.println("Street:" + raddress.getString("street"));
        JSONObject sndr = rpo_info.getJSONObject("sndr");
        System.out.println("Sender name:" + sndr.getString("name"));
        JSONObject sndr_address = rpo_info.getJSONObject("sndr_address");
        System.out.println("Sender index:" + sndr_address.getString("index"));
        JSONObject saddress = sndr_address.getJSONObject("address");
        System.out.println("Region:" + saddress.getString("region"));
        System.out.println("Area:" + saddress.getString("area"));
        System.out.println("Place:" + saddress.getString("place"));
        System.out.println("Street:" + saddress.getString("street"));
        JSONObject shouse = saddress.getJSONObject("house");
        System.out.println("Value:" + shouse.getString("value"));
    }

    private void swith(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
