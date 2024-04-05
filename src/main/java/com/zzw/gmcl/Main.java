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
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        List<download.VersionInfo> VersionList =download.get_versions();
        for (download.VersionInfo versionInfo : VersionList) {
            System.out.println(versionInfo.getId() + ", " + versionInfo.getType());
        }
        Scanner scanner = new Scanner(System.in);
        String version = scanner.nextLine();
        download.download_versions(version);
        displayDownloadQueue();
    }
    public static void displayDownloadQueue() {
        // 获取下载队列实例
        DownloadQueue downloadQueue = DownloadQueue.getInstance();

        // 获取队列中的所有项目并打印
        List<DownloadQueue.DownloadItem> queue = downloadQueue.getQueue();
        for (DownloadQueue.DownloadItem item : queue) {
            System.out.println("File Address: " + item.getFileAddress());
            System.out.println("File Type: " + item.getFileType());
            System.out.println("SHA1 Value: " + item.getSha1Value());
            System.out.println("Version: " + item.getVersion());
            System.out.println();
        }
    }
}