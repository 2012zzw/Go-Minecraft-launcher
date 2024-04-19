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

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Web {

    public static Future<String> renderWebPage(String url,boolean is_login_web) {

        // ����һ��CompletableFuture����
        CompletableFuture<String> future = new CompletableFuture<>();

        Platform.startup(() -> {
            // ���� Stage
            Stage stage = new Stage();
            stage.setTitle("Web Renderer");

            // ���� WebView ���
            WebView webView = new WebView();
            // ����ָ�� URL
            webView.getEngine().load(url);

            // ���� Scene������ WebView ��ӵ�����
            Scene scene = new Scene(webView, 800, 600);

            if(is_login_web) {
                // ���� WebView �ļ���״̬
                webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        // ��ȡ URL �е� code ����
                        String extractedCode = extractCode(webView.getEngine().getLocation());
                        if (extractedCode != null) {
                            // ����CompletableFuture�����ֵ
                            future.complete("1,"+extractedCode);

                            // ������Խ��н�һ���������罫 code �������ݸ���������
                            stage.close();
                        }
                    }
                });
            }

            stage.setOnCloseRequest(event -> {
                future.complete("0,null");
            });
            // ���� Scene������ʾ Stage
            stage.setScene(scene);
            stage.show();
            System.out.println("ok");
        });
        return future;
    }
    // ��ȡ URL �е� code �����ķ���
    private static String extractCode(String url) {
        Pattern pattern = Pattern.compile("\\bcode=([^&]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}

