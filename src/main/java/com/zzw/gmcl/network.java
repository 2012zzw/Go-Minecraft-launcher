package com.zzw.gmcl;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
public class network {
    public static JsonElement get_json(String url){
        JsonParser jsonParser = new JsonParser();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonElement jsonElement;
            try {
                jsonElement = jsonParser.parse(responseBody);
            }
            catch (Exception e){
                jsonElement = jsonParser.parse("{}");
            }
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            return jsonParser.parse("{}");
        }
    }
}
