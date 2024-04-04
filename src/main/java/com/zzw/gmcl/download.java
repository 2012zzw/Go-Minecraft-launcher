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
