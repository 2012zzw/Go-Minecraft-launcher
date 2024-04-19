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

import java.nio.file.Paths;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class download {
    static String url1="http://127.0.0.1:5000";//https://piston-meta.mojang.com
    static String path=".minecraft";
    public static List<VersionInfo> get_versions() {
        JsonElement versions_json=network.get_json(url1+"/mc/game/version_manifest.json","");
        JsonObject jsonObject = versions_json.getAsJsonObject();
        JsonArray versionsArray = jsonObject.getAsJsonArray("versions");
        List<VersionInfo> versionList = new ArrayList<>();
        for (JsonElement versionElement : versionsArray) {
            JsonObject versionObject = versionElement.getAsJsonObject();
            String id = versionObject.get("id").getAsString();
            String type = versionObject.get("type").getAsString();
            versionList.add(new VersionInfo(id, type));
        }
        for (VersionInfo versionInfo : versionList) {
            System.out.println("ID: " + versionInfo.getId() + ", Type: " + versionInfo.getType());
        }

        return versionList;
    }
    public static void download_versions(String version){
        JsonElement versions_json=network.get_json(url1+"/mc/game/version_manifest.json","");
        JsonObject jsonObject = versions_json.getAsJsonObject();
        JsonArray versionsArray = jsonObject.getAsJsonArray("versions");
        boolean is_id_true=false;
        JsonObject versionObject=null;
        for (JsonElement versionElement : versionsArray) {
            versionObject = versionElement.getAsJsonObject();
            String id = versionObject.get("id").getAsString();
            if (Objects.equals(id, version)){
                is_id_true=true;
                break;
            }
        }
        if (is_id_true==false){
            return;
        }
        else{
            String url = versionObject.get("url").toString();
            url = url.substring(1, url.length() - 1);
            JsonElement version_json=network.get_json(url,"");
            JsonObject version_jsonObject = version_json.getAsJsonObject();
            JsonObject client_object;
            JsonObject classifiersObject;
            client_object=version_jsonObject.get("downloads").getAsJsonObject().get("client").getAsJsonObject();
            String sha1 = client_object.get("sha1").toString();
            sha1 = sha1.substring(1, sha1.length() - 1);
            DownloadQueue.getInstance().addItem(client_object.get("url").toString(),
                    Paths.get(path, "versions",version,version+".jar").toString(),
                    "client",sha1, version);
            JsonArray libraries = version_jsonObject.getAsJsonArray("libraries");
            for (JsonElement librariesElement : libraries) {
                versionObject = librariesElement.getAsJsonObject().get("downloads").getAsJsonObject();
                if(versionObject.get("classifiers")==null){
                    DownloadQueue.getInstance().addItem(client_object.get("url").toString(),
                            Paths.get(path, "versions",version,version+".jar").toString(),
                            "client",sha1, version);
                }
                else{
                    classifiersObject = librariesElement.getAsJsonObject().get("classifiers").getAsJsonObject();
                    //if(classifiersObject.get("classifiers")!=null)
                }
            }
        }
    }
    static class VersionInfo {
        private String id;
        private String type;

        public VersionInfo(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }
        }

}
