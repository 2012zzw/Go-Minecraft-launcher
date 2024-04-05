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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private StringBuilder logBuilder;

    private Logger() {
        logBuilder = new StringBuilder();
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void log(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        logBuilder.append(timestamp).append(" - ").append(message).append("\n");
    }

    public synchronized String getLog() {
        return logBuilder.toString();
    }
}
