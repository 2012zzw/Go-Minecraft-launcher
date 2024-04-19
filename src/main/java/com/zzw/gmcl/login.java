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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

public class login {
    static String microsoft_url="https://login.live.com/oauth20_authorize.srf%20?client_id=00000000402b5328&response_type=code&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL%20&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf";
    static String microsoft_token_url="https://login.live.com/oauth20_token.srf";
    static String xbox_live_url="https://user.auth.xboxlive.com/user/authenticate";
    static String xbox_xsts_url="https://xsts.auth.xboxlive.com/xsts/authorize";
    static String minceraft_url="https://api.minecraftservices.com/authentication/login_with_xbox";
    static String archives_url="https://api.minecraftservices.com/minecraft/profile";
    public static String login_with_microsoft() {
        String[] code;
        try {
             code = Web.renderWebPage(microsoft_url, true).get().split(",", 2);;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        if(Objects.equals(code[0], "0"))
        {
            return null;
        }
        else{
            String body = "client_id=00000000402b5328&grant_type=authorization_code&redirect_uri=https://login.live.com/oauth20_desktop.srf&code="+code[1];
            JsonElement token_json = network.post_json(microsoft_token_url,body,"Content-Type:application/x-www-form-urlencoded");
            JsonObject toekn_obj = token_json.getAsJsonObject();
            String access_token = toekn_obj.get("access_token").toString();
            access_token = access_token.substring(1, access_token.length() - 1);

            String body_xbox = "{\"Properties\": {\"AuthMethod\": \"RPS\",\"SiteName\": \"user.auth.xboxlive.com\",\"RpsTicket\": \""+access_token+"\"},\"RelyingParty\": \"http://auth.xboxlive.com\",\"TokenType\": \"JWT\"}";
            JsonElement xbox_token_json = network.post_json(xbox_live_url,body_xbox,"Content-Type:application/json,Accept:application/json");
            JsonObject xbox_toekn_obj = xbox_token_json.getAsJsonObject();
            String token_xsts = xbox_toekn_obj.get("Token").toString();
            token_xsts = token_xsts.substring(1, token_xsts.length() - 1);

            String body_xsts = "{\"Properties\":{\"SandboxId\": \"RETAIL\",\"UserTokens\": [\""+token_xsts+"\"]},\"RelyingParty\": \"rp://api.minecraftservices.com/\",\"TokenType\": \"JWT\"}";
            JsonElement minceraft_token_json = network.post_json(xbox_xsts_url,body_xsts,"Content-Type:application/json,Accept:application/json");
            JsonObject minecraft_toekn_obj = minceraft_token_json.getAsJsonObject();
            String minecraft_token = minecraft_toekn_obj.get("Token").toString();
            minecraft_token = minecraft_token.substring(1, minecraft_token.length() - 1);
            String uhs = minecraft_toekn_obj.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").toString();
            uhs = uhs.substring(1, uhs.length() - 1);

            String body_minecraft = "{\"identityToken\" : \"XBL3.0 x="+uhs+";"+minecraft_token+"\",\"ensureLegacyEnabled\" : true}";
            JsonElement minceraft_json = network.post_json(minceraft_url,body_minecraft,"Content-Type:application/json");
            JsonObject minecraft_obj = minceraft_json.getAsJsonObject();
            String access_token_minecraft = minecraft_obj.get("access_token").toString();
            access_token_minecraft = access_token_minecraft.substring(1, access_token_minecraft.length() - 1);

            JsonElement archives_json = network.get_json(archives_url,"Authorization: Bearer "+access_token_minecraft);
            JsonObject archives_obj = archives_json.getAsJsonObject();
            return archives_obj.toString();
        }
    }
            }