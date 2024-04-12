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

public class login {
    static String code="";
    static String microsoft_url="https://login.live.com/oauth20_authorize.srf%20?client_id=00000000402b5328&response_type=code&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL%20&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf";
    public static void login_with_microsoft(){
        code="";
        Web.renderWebPage(microsoft_url,true);
    }
}