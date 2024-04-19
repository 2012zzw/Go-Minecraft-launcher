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

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class network {
    public static JsonElement get_json(String uri,String head){
        JsonParser jsonParser = new JsonParser();
        URI url = URI.create(uri);
        try {
            Logger.getInstance().log("Accessing " + url + " through get");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            // 解析头部信息字符串为 Map 对象
            Map<String, String> headersMap = parseHeaders(head);

            // 设置请求头部信息
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
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
    public static JsonElement post_json(String url, String postBody, String headers) {
        JsonParser jsonParser = new JsonParser();

        try {
            URI uri = URI.create(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri);

            // 解析头部信息字符串为 Map 对象
            Map<String, String> headersMap = parseHeaders(headers);

            // 设置请求头部信息
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }

            // 设置 POST 请求的正文
            StringEntity entity = new StringEntity(postBody);
            httpPost.setEntity(entity);

            // 执行 POST 请求并获取响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // 解析响应内容为 JSON
            String responseBody = EntityUtils.toString(responseEntity);
            JsonElement jsonResponse = jsonParser.parse(responseBody);

            return jsonResponse;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return jsonParser.parse("{}");
        } catch (IOException e) {
            e.printStackTrace();
            return jsonParser.parse("{}");
        } catch (IllegalArgumentException e) {
            System.err.println("URI 格式不正确：" + e.getMessage());
            return jsonParser.parse("{}");
        }
    }

    // 解析头部信息字符串为 Map 对象
    private static Map<String, String> parseHeaders(String headers) {
        Map<String, String> headersMap = new HashMap<>();
        String[] headerPairs = headers.split(",");
        for (String headerPair : headerPairs) {
            String[] keyValue = headerPair.split(":");
            if (keyValue.length == 2) {
                headersMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return headersMap;
    }
}
