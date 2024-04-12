/*
 * Go! Minecraft Launcher
 * Copyright (C) 2024  zhangziwen <zhangziwening@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.zzw.gmcl;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
public class network {
    public static JsonElement get_json(String uri){
        JsonParser jsonParser = new JsonParser();
        URI url = URI.create(uri);
        try {
            Logger.getInstance().log("Accessing "+"url"+" through get");
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
        } catch (IllegalArgumentException e){
            System.err.println("URI 格式不正确：" + e.getMessage());
            return jsonParser.parse("{}");
        }

    }
}
