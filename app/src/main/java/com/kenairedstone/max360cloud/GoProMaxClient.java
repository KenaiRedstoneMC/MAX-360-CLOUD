package com.kenairedstone.max360cloud;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

final class GoProMaxClient {
    static final String CAMERA_HOST = "10.5.5.9";
    private GoProMaxClient() {}

    static JSONObject getMediaList() throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(
                "http://" + CAMERA_HOST + ":8080/gp/gpMediaList").openConnection();
        c.setRequestMethod("GET");
        c.setConnectTimeout(5000);
        c.setReadTimeout(10000);
        c.setUseCaches(false);
        try {
            int code = c.getResponseCode();
            if (code != 200) throw new Exception("La GoPro respondió HTTP " + code);
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) body.append(line);
            reader.close();
            JSONObject json = new JSONObject(body.toString());
            if (!(json.opt("media") instanceof JSONArray)) {
                throw new Exception("La respuesta no contiene la lista de medios esperada.");
            }
            return json;
        } finally {
            c.disconnect();
        }
    }
}
