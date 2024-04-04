package com.zzw.gmcl;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
public class download {
        static String url="http://127.0.0.1:5000";
        public static List get_versions() {
            JsonElement versions_json=network.get_json(url+"/mc/game/version_manifest.json");
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
